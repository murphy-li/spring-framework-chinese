/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 版权所有2002-2019的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.springframework.lang.Nullable;

/**
 * Simple utility methods for dealing with streams. The copy methods of this class are
 * similar to those defined in {@link FileCopyUtils} except that all affected streams are
 * left open when done. All copy methods use a block size of 4096 bytes.
 *
 * <p>Mainly for use within the framework, but also useful for application code.
 *
 * @author Juergen Hoeller
 * @author Phillip Webb
 * @author Brian Clozel
 * @since 3.2.2
 * @see FileCopyUtils
 */
/**
 * 用于处理流的简单实用方法。 
 * 此类的复制方法与{@link  FileCopyUtils}中定义的复制方法类似，只不过在完成后所有受影响的流都保持打开状态。 
 * 所有复制方法都使用4096字节的块大小。 
 *  <p>主要在框架内使用，但对应用程序代码也很有用。 
 *  @author  Juergen Hoeller @author 菲利普·韦伯（Phillip Webb）@author  Brian Clozel @since 3.2.2 
 * @see  FileCopyUtils
 */
public abstract class StreamUtils {

	/**
	 * The default buffer size used why copying bytes.
	 */
	/**
	 * 默认缓冲区大小用于复制字节的原因。 
	 * 
	 */
	public static final int BUFFER_SIZE = 4096;

	private static final byte[] EMPTY_CONTENT = new byte[0];


	/**
	 * Copy the contents of the given InputStream into a new byte array.
	 * Leaves the stream open when done.
	 * @param in the stream to copy from (may be {@code null} or empty)
	 * @return the new byte array that has been copied to (possibly empty)
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 将给定InputStream的内容复制到新的字节数组中。 
	 * 完成后，使流保持打开状态。 
	 * 要从中复制的流中的
	 * @param （可以为{@code  null}或为空）
	 * @return 已复制到（可能为空）
	 * @throws  IOException的新字节数组（如果有I / O错误
	 */
	public static byte[] copyToByteArray(@Nullable InputStream in) throws IOException {
		if (in == null) {
			return new byte[0];
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		copy(in, out);
		return out.toByteArray();
	}

	/**
	 * Copy the contents of the given InputStream into a String.
	 * Leaves the stream open when done.
	 * @param in the InputStream to copy from (may be {@code null} or empty)
	 * @return the String that has been copied to (possibly empty)
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 将给定InputStream的内容复制到String中。 
	 * 完成后，使流保持打开状态。 
	 * 要从中复制（可能为{@code  null}或为空）的InputStream中的
	 * @param  
	 * @return 已复制到（可能为空）<
	 * @throws> IOException的字符串（如果发生I / O错误）
	 */
	public static String copyToString(@Nullable InputStream in, Charset charset) throws IOException {
		if (in == null) {
			return "";
		}

		StringBuilder out = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(in, charset);
		char[] buffer = new char[BUFFER_SIZE];
		int bytesRead = -1;
		while ((bytesRead = reader.read(buffer)) != -1) {
			out.append(buffer, 0, bytesRead);
		}
		return out.toString();
	}

	/**
	 * Copy the contents of the given byte array to the given OutputStream.
	 * Leaves the stream open when done.
	 * @param in the byte array to copy from
	 * @param out the OutputStream to copy to
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 将给定字节数组的内容复制到给定的OutputStream。 
	 * 完成后，使流保持打开状态。 
	 * 字节数组中的
	 * @param 要从
	 * @param 复制到OutputStream中，以在发生I / O错误时复制到
	 * @throws  IOException
	 */
	public static void copy(byte[] in, OutputStream out) throws IOException {
		Assert.notNull(in, "No input byte array specified");
		Assert.notNull(out, "No OutputStream specified");

		out.write(in);
	}

	/**
	 * Copy the contents of the given String to the given output OutputStream.
	 * Leaves the stream open when done.
	 * @param in the String to copy from
	 * @param charset the Charset
	 * @param out the OutputStream to copy to
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 将给定String的内容复制到给定输出OutputStream。 
	 * 完成后，使流保持打开状态。 
	 * 要从
	 * @param 复制的字符串中的
	 * @param 字符集，将Charset 
	 * @param 复制出OutputStream，以在发生I / O错误时复制到
	 * @throws  IOException
	 */
	public static void copy(String in, Charset charset, OutputStream out) throws IOException {
		Assert.notNull(in, "No input String specified");
		Assert.notNull(charset, "No charset specified");
		Assert.notNull(out, "No OutputStream specified");

		Writer writer = new OutputStreamWriter(out, charset);
		writer.write(in);
		writer.flush();
	}

	/**
	 * Copy the contents of the given InputStream to the given OutputStream.
	 * Leaves both streams open when done.
	 * @param in the InputStream to copy from
	 * @param out the OutputStream to copy to
	 * @return the number of bytes copied
	 * @throws IOException in case of I/O errors
	 */
	/**
	 * 将给定InputStream的内容复制到给定OutputStream。 
	 * 完成后，将两个流都保持打开状态。 
	 * 输入流中的
	 * @param 要从
	 * @param 复制到OutputStream中，要复制到
	 * @return 的字节数
	 * @throws  IOException如果发生I / O错误
	 */
	public static int copy(InputStream in, OutputStream out) throws IOException {
		Assert.notNull(in, "No InputStream specified");
		Assert.notNull(out, "No OutputStream specified");

		int byteCount = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
			byteCount += bytesRead;
		}
		out.flush();
		return byteCount;
	}

	/**
	 * Copy a range of content of the given InputStream to the given OutputStream.
	 * <p>If the specified range exceeds the length of the InputStream, this copies
	 * up to the end of the stream and returns the actual number of copied bytes.
	 * <p>Leaves both streams open when done.
	 * @param in the InputStream to copy from
	 * @param out the OutputStream to copy to
	 * @param start the position to start copying from
	 * @param end the position to end copying
	 * @return the number of bytes copied
	 * @throws IOException in case of I/O errors
	 * @since 4.3
	 */
	/**
	 * 将给定InputStream的内容范围复制到给定OutputStream。 
	 *  <p>如果指定范围超出InputStream的长度，则此操作将复制到流的末尾并返回实际的已复制字节数。 
	 *  <p>完成后，将两个流都保持打开状态。 
	 *  InputStream中的
	 * @param 从
	 * @param 复制出来，OutputStream中的复制到
	 * @param 开始位置，开始从
	 * @param 复制，结束位置，结束复制
	 * @return ，复制的字节数
	 * @throws 发生I / O错误时IOException，自4.3开始
	 */
	public static long copyRange(InputStream in, OutputStream out, long start, long end) throws IOException {
		Assert.notNull(in, "No InputStream specified");
		Assert.notNull(out, "No OutputStream specified");

		long skipped = in.skip(start);
		if (skipped < start) {
			throw new IOException("Skipped only " + skipped + " bytes out of " + start + " required");
		}

		long bytesToCopy = end - start + 1;
		byte[] buffer = new byte[(int) Math.min(StreamUtils.BUFFER_SIZE, bytesToCopy)];
		while (bytesToCopy > 0) {
			int bytesRead = in.read(buffer);
			if (bytesRead == -1) {
				break;
			}
			else if (bytesRead <= bytesToCopy) {
				out.write(buffer, 0, bytesRead);
				bytesToCopy -= bytesRead;
			}
			else {
				out.write(buffer, 0, (int) bytesToCopy);
				bytesToCopy = 0;
			}
		}
		return (end - start + 1 - bytesToCopy);
	}

	/**
	 * Drain the remaining content of the given InputStream.
	 * Leaves the InputStream open when done.
	 * @param in the InputStream to drain
	 * @return the number of bytes read
	 * @throws IOException in case of I/O errors
	 * @since 4.3
	 */
	/**
	 * 耗尽给定InputStream的剩余内容。 
	 * 完成后，将InputStream保持打开状态。 
	 * 输入流中的
	 * @param 消耗
	 * @return 读取的字节数
	 * @throws  IOException，以防I / O错误@从4.3开始
	 */
	public static int drain(InputStream in) throws IOException {
		Assert.notNull(in, "No InputStream specified");
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		int byteCount = 0;
		while ((bytesRead = in.read(buffer)) != -1) {
			byteCount += bytesRead;
		}
		return byteCount;
	}

	/**
	 * Return an efficient empty {@link InputStream}.
	 * @return a {@link ByteArrayInputStream} based on an empty byte array
	 * @since 4.2.2
	 */
	/**
	 * 返回有效的空{@link  InputStream}。 
	 *  
	 * @return 一个基于空字节数组的{@link  ByteArrayInputStream} @4.2.2起
	 */
	public static InputStream emptyInput() {
		return new ByteArrayInputStream(EMPTY_CONTENT);
	}

	/**
	 * Return a variant of the given {@link InputStream} where calling
	 * {@link InputStream#close() close()} has no effect.
	 * @param in the InputStream to decorate
	 * @return a version of the InputStream that ignores calls to close
	 */
	/**
	 * 返回给定{@link  InputStream}的变体，其中调用{@link  InputStream＃close（）close（）}无效。 
	 * 在InputStream中使用
	 * @param 修饰
	 * @return 一个InputStream版本，该版本将忽略关闭调用
	 */
	public static InputStream nonClosing(InputStream in) {
		Assert.notNull(in, "No InputStream specified");
		return new NonClosingInputStream(in);
	}

	/**
	 * Return a variant of the given {@link OutputStream} where calling
	 * {@link OutputStream#close() close()} has no effect.
	 * @param out the OutputStream to decorate
	 * @return a version of the OutputStream that ignores calls to close
	 */
	/**
	 * 返回给定{@link  OutputStream}的变体，其中调用{@link  OutputStream＃close（）close（）}无效。 
	 *  
	 * @param 在OutputStream之外装饰
	 * @return 一个OutputStream版本，该版本忽略关闭调用
	 */
	public static OutputStream nonClosing(OutputStream out) {
		Assert.notNull(out, "No OutputStream specified");
		return new NonClosingOutputStream(out);
	}


	private static class NonClosingInputStream extends FilterInputStream {

		public NonClosingInputStream(InputStream in) {
			super(in);
		}

		@Override
		public void close() throws IOException {
		}
	}


	private static class NonClosingOutputStream extends FilterOutputStream {

		public NonClosingOutputStream(OutputStream out) {
			super(out);
		}

		@Override
		public void write(byte[] b, int off, int let) throws IOException {
			// It is critical that we override this method for performance
			this.out.write(b, off, let);
		}

		@Override
		public void close() throws IOException {
		}
	}

}
