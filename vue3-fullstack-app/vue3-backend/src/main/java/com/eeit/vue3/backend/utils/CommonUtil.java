package com.eeit.vue3.backend.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * 通用工具類別
 * <p>
 * 提供 byte[] 處理、Base64 轉換、MIME Type 猜測等輔助方法。
 */
public class CommonUtil {

	/**
	 * 判斷傳入的 byte[] 是否有值。
	 */
	public static boolean isByteArrayNotBlank(byte[] bytes) {
		return !isByteArrayBlank(bytes);
	}

	/**
	 * 判斷傳入的 byte[] 是否為空。
	 */
	public static boolean isByteArrayBlank(byte[] bytes) {
		return bytes == null || bytes.length == 0;
	}

	/**
	 * 根據傳入的圖片 byte[] 猜測其 MIME type，猜不中則一律回傳 image/jpeg
	 */
	public static String getImageMimeType(byte[] imageBytes) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
			String mimeType = URLConnection.guessContentTypeFromStream(bais);
			bais.close();

			return mimeType;

		} catch (IOException e) {
			log.warn("猜不到 MIME Type!");
			return "image/jpeg";
		}
	}

	/**
	 * 將傳入的 byte[] 轉型成 Base64 格式。
	 */
	public static String encodeToBase64String(byte[] bytes) {
		String base64String = Base64.getEncoder().encodeToString(bytes);
		return base64String;
	}

	/**
	 * 結合以上兩個方法，取得 Base64 格式的圖片字串
	 */
	public static String getBase64Image(byte[] bytes) {

		String mimeType = CommonUtil.getImageMimeType(bytes);
		String base64String = CommonUtil.encodeToBase64String(bytes);

		return "data:%s;base64,%s".formatted(mimeType, base64String);

	}
}
