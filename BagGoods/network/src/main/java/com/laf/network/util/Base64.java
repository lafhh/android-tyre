/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package com.laf.network.util;

import java.io.ByteArrayOutputStream;

/**
 *
 */
public class Base64 {
	
	//64位定义
	private static final char[] ENCODE_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
	        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
	        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
	        '7', '8', '9', '+', '/' };
	
	//解码定义
	private static final byte[] DECODE_BYTES = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1,
	        63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
	        12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33,
	        34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };
	
	private Base64() {
	}
	
	//加密
	public static String encode(String encodingStr) {
		byte[] byteArr = encodingStr.getBytes();
		StringBuffer sb = new StringBuffer();
		int len = byteArr.length;
		int i = 0;
		int b1, b2, b3;
		
		while (i < len) {
			b1 = byteArr[i++] & 0xff;
			if (i == len) {
				sb.append(ENCODE_CHARS[b1 >>> 2]);
				sb.append(ENCODE_CHARS[(b1 & 0x03) << 4]);
				sb.append("==");
				break;
			}
			b2 = byteArr[i++] & 0xff;
			if (i == len) {
				sb.append(ENCODE_CHARS[b1 >>> 2]);
				sb.append(ENCODE_CHARS[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
				sb.append(ENCODE_CHARS[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = byteArr[i++] & 0xff;
			sb.append(ENCODE_CHARS[b1 >>> 2]);
			sb.append(ENCODE_CHARS[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
			sb.append(ENCODE_CHARS[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
			sb.append(ENCODE_CHARS[b3 & 0x3f]);
		}
		
		return sb.toString();
	}
	
	//解密
	public static String decode(String decodingStr) {
		byte[] data = decodingStr.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;
		
		while (i < len) {
			
			/* b1 */
			do {
				b1 = DECODE_BYTES[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1) {
				break;
			}
			
			/* b2 */
			do {
				b2 = DECODE_BYTES[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1) {
				break;
			}
			buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
			
			/* b3 */
			do {
				b3 = data[i++];
				if (b3 == 61) {
					return buf.toString();
				}
				b3 = DECODE_BYTES[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1) {
				break;
			}
			buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
			
			/* b4 */
			do {
				b4 = data[i++];
				if (b4 == 61) {
					return buf.toString();
				}
				b4 = DECODE_BYTES[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1) {
				break;
			}
			buf.write((int) (((b3 & 0x03) << 6) | b4));
		}
		return buf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(decode(encode("卫桥").trim()));
	}
}
