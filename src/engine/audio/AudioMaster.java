package engine.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.util.Log;
import org.lwjgl.openal.*;

import org.lwjgl.openal.AL;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioMaster {

	private static long device, context;
	private static boolean init = false;
	private static Vec3f listenerPosition, listenerVelocity;
	public static List<AudioSource> audioSources = new ArrayList<>();

	public static void init(Vec3f position, Vec3f velocity){
		init = true;
		device = alcOpenDevice((ByteBuffer) null);
		if (device == NULL) {
			Log.logError("Failed to open the default OpenAL device");
		}
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		context = alcCreateContext(device, (IntBuffer) null);
		if (context == NULL) {
			Log.logError("Failed to create OpenAL context");
		}
		alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);
		setPosition(position);
		setVelocity(velocity);
	}

	public static void setDistanceAttenuation(int model){
		AL10.alDistanceModel(model);

	}

	public static void setPosition(Vec3f position) {
		alListener3f(AL_POSITION, position.x(), position.y(), position.z());
		listenerPosition = position;
	}

	public static void setVelocity(Vec3f velocity){
		alListener3f(AL_VELOCITY, velocity.x(), velocity.y(), velocity.z());
		listenerVelocity = velocity;
	}

	public static Vec3f getListenerPosition() {
		return listenerPosition;
	}

	public static Vec3f getListenerVelocity() {
		return listenerVelocity;
	}

	public static void cleanUpMemory(){
		if(init) {
			for (AudioSource audioSource : audioSources) {
				audioSource.cleanUpMemory();
			}
			alcDestroyContext(context);
			alcCloseDevice(device);
			audioSources.clear();
		}
	}
}