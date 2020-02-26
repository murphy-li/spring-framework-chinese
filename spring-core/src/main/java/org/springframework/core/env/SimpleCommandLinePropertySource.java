/** Generated by english-annotation-buster, Powered by Google Translate.**/
/*
 * Copyright 2002-2020 the original author or authors.
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
 * 版权所有2002-2020的原始作者或作者。 
 * 根据Apache许可证2.0版（"许可证"）获得许可； 
 * 除非遵守许可，否则不得使用此文件。 
 * 您可以在https://www.apache.org/licenses/LICENSE-2.0上获得许可的副本。 
 * 除非适用法律要求或以书面形式同意，否则根据"许可"分发的软件将按"现状"分发，没有任何明示或暗示的保证或条件。 
 * 有关许可下特定的语言管理权限和限制，请参阅许可。 
 * 
 */

package org.springframework.core.env;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * {@link CommandLinePropertySource} implementation backed by a simple String array.
 *
 * <h3>Purpose</h3>
 * <p>This {@code CommandLinePropertySource} implementation aims to provide the simplest
 * possible approach to parsing command line arguments. As with all {@code
 * CommandLinePropertySource} implementations, command line arguments are broken into two
 * distinct groups: <em>option arguments</em> and <em>non-option arguments</em>, as
 * described below <em>(some sections copied from Javadoc for
 * {@link SimpleCommandLineArgsParser})</em>:
 *
 * <h3>Working with option arguments</h3>
 * <p>Option arguments must adhere to the exact syntax:
 *
 * <pre class="code">--optName[=optValue]</pre>
 *
 * <p>That is, options must be prefixed with "{@code --}" and may or may not
 * specify a value. If a value is specified, the name and value must be separated
 * <em>without spaces</em> by an equals sign ("="). The value may optionally be
 * an empty string.
 *
 * <h4>Valid examples of option arguments</h4>
 * <pre class="code">
 * --foo
 * --foo=
 * --foo=""
 * --foo=bar
 * --foo="bar then baz"
 * --foo=bar,baz,biz</pre>
 *
 * <h4>Invalid examples of option arguments</h4>
 * <pre class="code">
 * -foo
 * --foo bar
 * --foo = bar
 * --foo=bar --foo=baz --foo=biz</pre>
 *
 * <h3>Working with non-option arguments</h3>
 * <p>Any and all arguments specified at the command line without the "{@code --}"
 * option prefix will be considered as "non-option arguments" and made available
 * through the {@link CommandLineArgs#getNonOptionArgs()} method.
 *
 * <h3>Typical usage</h3>
 * <pre class="code">
 * public static void main(String[] args) {
 *     PropertySource<?> ps = new SimpleCommandLinePropertySource(args);
 *     // ...
 * }</pre>
 *
 * See {@link CommandLinePropertySource} for complete general usage examples.
 *
 * <h3>Beyond the basics</h3>
 *
 * <p>When more fully-featured command line parsing is necessary, consider using
 * the provided {@link JOptCommandLinePropertySource}, or implement your own
 * {@code CommandLinePropertySource} against the command line parsing library of your
 * choice.
 *
 * @author Chris Beams
 * @since 3.1
 * @see CommandLinePropertySource
 * @see JOptCommandLinePropertySource
 */
/**
 * 由简单的String数组支持的{@link  CommandLinePropertySource}实现。 
 *  <h3>目的</ h3> <p>此{@code  CommandLinePropertySource}实现旨在为解析命令行参数提供最简单的方法。 
 * 与所有{@code  CommandLinePropertySource}实现一样，命令行参数分为两个不同的组：<em>选项参数</ em>和<em>非选项参数</ em>，如下所述<em> （某些部分是从Javadoc复制的，用于{@link  SimpleCommandLineArgsParser}）</ em>：<h3>使用选项参数</ h3> <p>选项参数必须遵循确切的语法：<pre class ="code">-optName [= optValue] </ pre> <p>也就是说，选项必须以"{@code -}"为前缀，并且可以指定也可以不指定值。 
 * 如果指定了值，则名称和值必须用等号（"="）<em>隔开</ em>。 
 * 该值可以是一个空字符串。 
 *  <h4>选项参数的有效示例</ h4> <pre class ="code"> --foo --foo = --foo =""--foo = bar --foo ="bar然后baz"--foo = bar，baz，biz </ pre> <h4>选项参数的无效示例</ h4> <pre class ="code"> -foo --foo bar --foo = bar --foo = bar --foo = baz --foo = biz </ pre> <h3>使用非选项参数</ h3> <p>任何和所有在命令行中指定且不带"{@code -}"选项前缀的参数都将被视为"非选项参数"，并可以通过{@link  CommandLineArgs＃getNonOptionArgs（）}方法使用。 
 *  <h3>典型用法</ h3> <pre class ="code"> public static void main（String [] args）{PropertySource <？> ps = new SimpleCommandLinePropertySource（args）; // ...} </ pre>有关完整的常规用法示例，请参见{@link  CommandLinePropertySource}。 
 *  <h3>超出基础知识</ h3> <p>当需要更全功能的命令行解析时，请考虑使用提供的{@link  JOptCommandLinePropertySource}，或针对命令实现自己的{@code  CommandLinePropertySource}您选择的行解析库。 
 *  @author 克里斯·比姆斯（Chris Beams）自3.1起
 * @see  CommandLinePropertySource 
 * @see  JOptCommandLinePropertySource
 */
public class SimpleCommandLinePropertySource extends CommandLinePropertySource<CommandLineArgs> {

	/**
	 * Create a new {@code SimpleCommandLinePropertySource} having the default name
	 * and backed by the given {@code String[]} of command line arguments.
	 * @see CommandLinePropertySource#COMMAND_LINE_PROPERTY_SOURCE_NAME
	 * @see CommandLinePropertySource#CommandLinePropertySource(Object)
	 */
	/**
	 * 创建一个新的{@code  SimpleCommandLinePropertySource}，具有默认名称，并以给定的命令行参数{@code  String []}为后盾。 
	 *  
	 * @see  CommandLinePropertySource＃COMMAND_LINE_PROPERTY_SOURCE_NAME 
	 * @see  CommandLinePropertySource＃CommandLinePropertySource（Object）
	 */
	public SimpleCommandLinePropertySource(String... args) {
		super(new SimpleCommandLineArgsParser().parse(args));
	}

	/**
	 * Create a new {@code SimpleCommandLinePropertySource} having the given name
	 * and backed by the given {@code String[]} of command line arguments.
	 */
	/**
	 * 创建一个新的{@code  SimpleCommandLinePropertySource}，具有给定的名称，并以给定的命令行参数{@code  String []}为后盾。 
	 * 
	 */
	public SimpleCommandLinePropertySource(String name, String[] args) {
		super(name, new SimpleCommandLineArgsParser().parse(args));
	}

	/**
	 * Get the property names for the option arguments.
	 */
	/**
	 * 获取选项参数的属性名称。 
	 * 
	 */
	@Override
	public String[] getPropertyNames() {
		return StringUtils.toStringArray(this.source.getOptionNames());
	}

	@Override
	protected boolean containsOption(String name) {
		return this.source.containsOption(name);
	}

	@Override
	@Nullable
	protected List<String> getOptionValues(String name) {
		return this.source.getOptionValues(name);
	}

	@Override
	protected List<String> getNonOptionArgs() {
		return this.source.getNonOptionArgs();
	}

}
