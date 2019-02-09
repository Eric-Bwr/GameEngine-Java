package engine.audio;

import engine.maths.Vec3f;
import engine.util.BufferUtil;
import engine.util.Log;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_close;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioSource {

	private int sourceId, bufferId;

	private ShortBuffer pcm = null;

	private ByteBuffer vorbis = null;

	private float rollOffFactor, referenceDistance, maxDistance;

	private Vec3f position;

	public AudioSource(String file) {
		AudioMaster.audioSources.add(this);
		this.bufferId = alGenBuffers();
		try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
			try {
				pcm = readVorbis(file, 32 * 1024, info);
			} catch (Exception e){
				Log.logError("Failed to create AudioBuffer: " + file);
			}
			alBufferData(bufferId, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
		}
		this.sourceId = alGenSources();
		alSourcei(sourceId, AL_BUFFER, bufferId);
	}

	public void setRollOffFactor(float factor){
		alSourcef(sourceId, AL_ROLLOFF_FACTOR, factor);
		this.rollOffFactor = factor;
	}

	public  void setReferenceDistance(float distance){
		alSourcef(sourceId, AL_REFERENCE_DISTANCE, distance);
		this.referenceDistance = distance;
	}

	public void setMaxDistance(float distance){
		alSourcef(sourceId, AL_MAX_DISTANCE, distance);
		this.maxDistance = distance;
	}

	//TODO: getter

	public void setLooping(boolean loop){
		if(loop) {
			alSourcei(sourceId, AL_LOOPING, AL_TRUE);

		}else{
			alSourcei(sourceId, AL_LOOPING, AL_FALSE);

		}
	}

	public void setRelative(boolean relative){
		if(relative) {
			alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);

		}else{
			alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_FALSE);

		}
	}

	public void setPosition(Vec3f position) {
		alSource3f(sourceId, AL_POSITION, position.x(), position.y(), position.z());
		this.position = position;
	}

	public void setVelocity(Vec3f velocity) {
		alSource3f(sourceId, AL_VELOCITY, velocity.x(), velocity.y(), velocity.z());
	}

	public void setPitch(float pitch) {
		alSourcef(sourceId, AL_PITCH, pitch);
	}

	public void setGain(float gain) {
		alSourcef(sourceId, AL_GAIN, gain);
	}

	public void setProperty(int param, float value) {
		alSourcef(sourceId, param, value);
	}

	public Vec3f getPosition() {
		return position;
	}

	public void play() {
		stop();
		alSourcePlay(sourceId);
	}

	public boolean isPlaying() {
		return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
	}

	public void pause() {
		alSourcePause(sourceId);
	}

	public void stop() {
		alSourceStop(sourceId);
	}

	public void cleanUpMemory() {
		stop();
		alDeleteBuffers(this.bufferId);
		if (pcm != null) {
			MemoryUtil.memFree(pcm);
		}
		alDeleteSources(sourceId);
	}

	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			vorbis = BufferUtil.ioResourceToByteBuffer(resource, bufferSize);
			IntBuffer error = stack.mallocInt(1);
			long decoder = stb_vorbis_open_memory(vorbis, error, null);
			if (decoder == NULL) {
				Log.logError("Failed to open Ogg Vorbis file. Error: " + error.get(0));
			}

			stb_vorbis_get_info(decoder, info);

			int channels = info.channels();

			int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

			pcm = MemoryUtil.memAllocShort(lengthSamples);

			pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
			stb_vorbis_close(decoder);

			return pcm;
		}
	}
}