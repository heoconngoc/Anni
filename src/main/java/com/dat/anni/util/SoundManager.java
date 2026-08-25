package com.dat.anni.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Âm thanh hiệu ứng dùng chung. Ưu tiên file thật trong resources/sfx
 * (click.wav, game-over.wav, win.wav); thiếu thì tự tổng hợp âm đơn giản
 * để app luôn có tiếng mà không cần asset.
 */
public final class SoundManager {

	public enum Sfx {
		CLICK("click"),
		GAME_OVER("game-over"),
		WIN("win");

		private final String file;

		Sfx(String file) {
			this.file = file;
		}
	}

	private static final float SAMPLE_RATE = 22_050f;

	private SoundManager() {
	}

	public static void play(Sfx sfx) {
		new Thread(() -> {
			try {
				AudioInputStream stream = streamFor(sfx);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.addLineListener(e -> {
					if (e.getType().toString().equals("STOP")) {
						clip.close();
					}
				});
				clip.setFramePosition(0);
				clip.start();
			} catch (Exception ignored) {
				// âm thanh là phụ, không làm ảnh hưởng game
			}
		}, "sfx-" + sfx.file).start();
	}

	private static AudioInputStream streamFor(Sfx sfx) throws Exception {
		var url = SoundManager.class.getResource("/sfx/" + sfx.file + ".wav");
		if (url != null) {
			return AudioSystem.getAudioInputStream(url);
		}
		return synthesized(sfx);
	}

	private static AudioInputStream synthesized(Sfx sfx) {
		ByteArrayOutputStream pcm = new ByteArrayOutputStream();
		switch (sfx) {
			case CLICK -> tone(pcm, 1100, 55, 0.55);
			case GAME_OVER -> {
				tone(pcm, 392, 140, 0.6);
				tone(pcm, 294, 140, 0.6);
				tone(pcm, 196, 260, 0.65);
			}
			case WIN -> {
				tone(pcm, 523, 110, 0.6);
				tone(pcm, 659, 110, 0.6);
				tone(pcm, 784, 110, 0.6);
				tone(pcm, 1047, 240, 0.7);
			}
		}
		byte[] data = pcm.toByteArray();
		AudioFormat format = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
		return new AudioInputStream(new ByteArrayInputStream(data), format, data.length);
	}

	private static void tone(ByteArrayOutputStream out, int hz, int ms, double volume) {
		int samples = (int) (SAMPLE_RATE * ms / 1000);
		for (int i = 0; i < samples; i++) {
			double envelope = 1.0 - (double) i / samples; // giảm dần để không tắc tai
			double value = Math.sin(2 * Math.PI * hz * i / SAMPLE_RATE) * envelope * volume;
			out.write((byte) (value * Byte.MAX_VALUE));
		}
	}
}
