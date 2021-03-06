/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2018 the original author or authors.
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
 * 版权所有2002-2018的原始作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.web.util;

/**
 * Utility class for JavaScript escaping.
 * Escapes based on the JavaScript 1.5 recommendation.
 *
 * <p>Reference:
 * <a href="https://developer.mozilla.org/en-US/docs/JavaScript/Guide/Values,_variables,_and_literals#String_literals">
 * JavaScript Guide</a> on Mozilla Developer Network.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Rossen Stoyanchev
 * @since 1.1.1
 */
/**
 * JavaScript转义的实用程序类。 
 * 根据JavaScript 1.5建议进行转义。 
 *  <p>参考：Mozilla开发人员网络上的<a href="https://developer.mozilla.org/zh-CN/docs/JavaScript/Guide/Values,_variables,_and_literals#String_literals"> JavaScript指南</a>。 
 *  @author  Juergen Hoeller @author  Rob Harrop @author  Rossen Stoyanchev @1.1.1起
 */
public abstract class JavaScriptUtils {

	/**
	 * Turn JavaScript special characters into escaped characters.
	 * @param input the input string
	 * @return the string with escaped characters
	 */
	/**
	 * 将JavaScript特殊字符转换为转义字符。 
	 *  
	 * @param 输入输入字符串
	 * @return 带有转义字符的字符串
	 */
	public static String javaScriptEscape(String input) {
		StringBuilder filtered = new StringBuilder(input.length());
		char prevChar = '\u0000';
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '"') {
				filtered.append("\\\"");
			}
			else if (c == '\'') {
				filtered.append("\\'");
			}
			else if (c == '\\') {
				filtered.append("\\\\");
			}
			else if (c == '/') {
				filtered.append("\\/");
			}
			else if (c == '\t') {
				filtered.append("\\t");
			}
			else if (c == '\n') {
				if (prevChar != '\r') {
					filtered.append("\\n");
				}
			}
			else if (c == '\r') {
				filtered.append("\\n");
			}
			else if (c == '\f') {
				filtered.append("\\f");
			}
			else if (c == '\b') {
				filtered.append("\\b");
			}
			// No '\v' in Java, use octal value for VT ascii char
			else if (c == '\013') {
				filtered.append("\\v");
			}
			else if (c == '<') {
				filtered.append("\\u003C");
			}
			else if (c == '>') {
				filtered.append("\\u003E");
			}
			// Unicode for PS (line terminator in ECMA-262)
			else if (c == '\u2028') {
				filtered.append("\\u2028");
			}
			// Unicode for LS (line terminator in ECMA-262)
			else if (c == '\u2029') {
				filtered.append("\\u2029");
			}
			else {
				filtered.append(c);
			}
			prevChar = c;

		}
		return filtered.toString();
	}

}
