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

package org.springframework.http.server.reactive;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSession;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Default implementation of {@link SslInfo}.
 *
 * @author Rossen Stoyanchev
 * @since 5.0.2
 */
/**
 * {@link  SslInfo}的默认实现。 
 *  @author  Rossen Stoyanchev @5.0.2起
 */
final class DefaultSslInfo implements SslInfo {

	@Nullable
	private final String sessionId;

	@Nullable
	private final X509Certificate[] peerCertificates;


	DefaultSslInfo(@Nullable String sessionId, X509Certificate[] peerCertificates) {
		Assert.notNull(peerCertificates, "No SSL certificates");
		this.sessionId = sessionId;
		this.peerCertificates = peerCertificates;
	}

	DefaultSslInfo(SSLSession session) {
		Assert.notNull(session, "SSLSession is required");
		this.sessionId = initSessionId(session);
		this.peerCertificates = initCertificates(session);
	}


	@Override
	@Nullable
	public String getSessionId() {
		return this.sessionId;
	}

	@Override
	@Nullable
	public X509Certificate[] getPeerCertificates() {
		return this.peerCertificates;
	}


	@Nullable
	private static String initSessionId(SSLSession session) {
		byte [] bytes = session.getId();
		if (bytes == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			String digit = Integer.toHexString(b);
			if (digit.length() < 2) {
				sb.append('0');
			}
			if (digit.length() > 2) {
				digit = digit.substring(digit.length() - 2);
			}
			sb.append(digit);
		}
		return sb.toString();
	}

	@Nullable
	private static X509Certificate[] initCertificates(SSLSession session) {
		Certificate[] certificates;
		try {
			certificates = session.getPeerCertificates();
		}
		catch (Throwable ex) {
			return null;
		}

		List<X509Certificate> result = new ArrayList<>(certificates.length);
		for (Certificate certificate : certificates) {
			if (certificate instanceof X509Certificate) {
				result.add((X509Certificate) certificate);
			}
		}
		return (!result.isEmpty() ? result.toArray(new X509Certificate[0]) : null);
	}

}
