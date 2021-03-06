package com.tao.jimmy.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * 將圖形縮小後回傳，如果發生錯誤就直接回傳原圖， 例如：imageSize值<=1、無法取得圖的寬高、發生Exception
	 * 
	 * @param srcImageData
	 *            來源圖形資料
	 * @param scaleSize
	 *            欲將圖形縮至多少尺寸以下，50代表將尺寸縮小至50x50以內
	 * @return 縮小完畢的圖形資料
	 */
	public static byte[] shrink(byte[] srcImageData, int scaleSize) {
		ByteArrayInputStream bais = new ByteArrayInputStream(srcImageData);
		ByteArrayOutputStream baos = null;
		// 縮小比例，4代表除以4
		double sampleSize = 1;
		double imageWidth = 0;
		double imageHeight = 0;

		// 如果imageSize為0、負數(代表錯誤)或1(1代表與原圖一樣大小)，就直接回傳原圖資料
		if (scaleSize <= 1) {
			return srcImageData;
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(bais);
			// 如果無法識別圖檔格式(TYPE_CUSTOM)就回傳TYPE_INT_ARGB；否則回傳既有格式
			int type = srcBufferedImage.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_ARGB
					: srcBufferedImage.getType();
			imageWidth = srcBufferedImage.getWidth();
			imageHeight = srcBufferedImage.getHeight();
			if (imageWidth == 0 || imageHeight == 0) {
				return srcImageData;
			}
			// 只要圖檔較長的一邊超過指定長度(desireSize)，就計算欲縮小的比例
			// 並將圖檔寬高都除以欲縮小比例
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
