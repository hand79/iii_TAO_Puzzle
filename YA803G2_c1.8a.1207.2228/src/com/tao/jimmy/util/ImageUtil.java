package com.tao.jimmy.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * �N�ϧ��Y�p��^�ǡA�p�G�o�Ϳ��~�N�����^�ǭ�ϡA �Ҧp�GimageSize��<=1�B�L�k���o�Ϫ��e���B�o��Exception
	 * 
	 * @param srcImageData
	 *            �ӷ��ϧθ��
	 * @param scaleSize
	 *            ���N�ϧ��Y�ܦh�֤ؤo�H�U�A50�N��N�ؤo�Y�p��50x50�H��
	 * @return �Y�p�������ϧθ��
	 */
	public static byte[] shrink(byte[] srcImageData, int scaleSize) {
		ByteArrayInputStream bais = new ByteArrayInputStream(srcImageData);
		ByteArrayOutputStream baos = null;
		// �Y�p��ҡA4�N���H4
		double sampleSize = 1;
		double imageWidth = 0;
		double imageHeight = 0;

		// �p�GimageSize��0�B�t��(�N����~)��1(1�N��P��Ϥ@�ˤj�p)�A�N�����^�ǭ�ϸ��
		if (scaleSize <= 1) {
			return srcImageData;
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(bais);
			// �p�G�L�k�ѧO���ɮ榡(TYPE_CUSTOM)�N�^��TYPE_INT_ARGB�F�_�h�^�ǬJ���榡
			int type = srcBufferedImage.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_ARGB
					: srcBufferedImage.getType();
			imageWidth = srcBufferedImage.getWidth();
			imageHeight = srcBufferedImage.getHeight();
			if (imageWidth == 0 || imageHeight == 0) {
				return srcImageData;
			}
			// �u�n���ɸ������@��W�L���w����(desireSize)�A�N�p����Y�p�����
			// �ñN���ɼe�������H���Y�p���
			double longer = Math.max(imageWidth, imageHeight);
			if (longer > scaleSize) {
				sampleSize = longer / scaleSize;
//				System.out.println("scaleSize=" + scaleSize);
//				System.out.println("longer=" + longer);
//				System.out.println("sampleSize=" + sampleSize);
				imageWidth = srcBufferedImage.getWidth() / sampleSize;
				imageHeight = srcBufferedImage.getHeight() / sampleSize;
			}
			BufferedImage scaledBufferedImage = new BufferedImage((int)imageWidth,
					(int)imageHeight, type);
			Graphics graphics = scaledBufferedImage.createGraphics();
			graphics.drawImage(srcBufferedImage, 0, 0, (int)imageWidth, (int)imageHeight,
					null);
			baos = new ByteArrayOutputStream();
			ImageIO.write(scaledBufferedImage, "jpg", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return srcImageData;
		} finally {
			try {
				if (bais != null)
					bais.close();
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
