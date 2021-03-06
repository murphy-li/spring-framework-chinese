/** Generated by english-annotation-buster, Powered by Google Translate.**/
// ASM: a very small and fast Java bytecode manipulation framework
// Copyright (c) 2000-2011 INRIA, France Telecom
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
// 3. Neither the name of the copyright holders nor the names of its
//    contributors may be used to endorse or promote products derived from
//    this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
// THE POSSIBILITY OF SUCH DAMAGE.
package org.springframework.asm;

/**
 * A visitor to visit a Java annotation. The methods of this class must be called in the following
 * order: ( {@code visit} | {@code visitEnum} | {@code visitAnnotation} | {@code visitArray} )*
 * {@code visitEnd}.
 *
 * @author Eric Bruneton
 * @author Eugene Kuleshov
 */
/**
 * 访客访问Java注释。 
 * 此类的方法必须按以下顺序调用：（{{@@code> visit} | {@code  visitEnum} | {@code  visitAnnotation} | {@code  visitArray}）{<@code > visitEnd}。 
 *  @author 埃里克·布鲁内顿@author 尤金·库列肖夫
 */
public abstract class AnnotationVisitor {

  /**
   * The ASM API version implemented by this visitor. The value of this field must be one of {@link
   * Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
   */
  /**
   * 此访问者实现的ASM API版本。 
   * 该字段的值必须是{@link  Opcodes＃ASM4}，{<@link> Opcodes＃ASM5}，{<@link> Opcodes＃ASM6}或{@link  Opcodes＃ASM7}之一。 
   * 
   */
  protected final int api;

  /**
   * The annotation visitor to which this visitor must delegate method calls. May be {@literal
   * null}.
   */
  /**
   * 此访问者必须委派方法调用的注释访问者。 
   * 可能为{@literal null}。 
   * 
   */
  protected AnnotationVisitor av;

  /**
   * Constructs a new {@link AnnotationVisitor}.
   *
   * @param api the ASM API version implemented by this visitor. Must be one of {@link
   *     Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
   */
  /**
   * 构造一个新的{@link  AnnotationVisitor}。 
   *  
   * @param  api此访客实现的ASM API版本。 
   * 必须是{@link  Opcodes＃ASM4}，{<@link> Opcodes＃ASM5}，{<@link> Opcodes＃ASM6}或{@link  Opcodes＃ASM7}之一。 
   * 
   */
  public AnnotationVisitor(final int api) {
    this(api, null);
  }

  /**
   * Constructs a new {@link AnnotationVisitor}.
   *
   * @param api the ASM API version implemented by this visitor. Must be one of {@link
   *     Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
   * @param annotationVisitor the annotation visitor to which this visitor must delegate method
   *     calls. May be {@literal null}.
   */
  /**
   * 构造一个新的{@link  AnnotationVisitor}。 
   *  
   * @param  api此访客实现的ASM API版本。 
   * 必须是{@link  Opcodes＃ASM4}，{<@link> Opcodes＃ASM5}，{<@link> Opcodes＃ASM6}或{@link  Opcodes＃ASM7}之一。 
   *  
   * @param 注解访问此访问者必须委派方法调用的注解访问者。 
   * 可能为{@literal null}。 
   * 
   */
  @SuppressWarnings("deprecation")
  public AnnotationVisitor(final int api, final AnnotationVisitor annotationVisitor) {
    if (api != Opcodes.ASM7
        && api != Opcodes.ASM6
        && api != Opcodes.ASM5
        && api != Opcodes.ASM4
        && api != Opcodes.ASM8_EXPERIMENTAL) {
      throw new IllegalArgumentException("Unsupported api " + api);
    }
    if (api == Opcodes.ASM8_EXPERIMENTAL) {
      Constants.checkAsm8Experimental(this);
    }
    this.api = api;
    this.av = annotationVisitor;
  }

  /**
   * Visits a primitive value of the annotation.
   *
   * @param name the value name.
   * @param value the actual value, whose type must be {@link Byte}, {@link Boolean}, {@link
   *     Character}, {@link Short}, {@link Integer} , {@link Long}, {@link Float}, {@link Double},
   *     {@link String} or {@link Type} of {@link Type#OBJECT} or {@link Type#ARRAY} sort. This
   *     value can also be an array of byte, boolean, short, char, int, long, float or double values
   *     (this is equivalent to using {@link #visitArray} and visiting each array element in turn,
   *     but is more convenient).
   */
  /**
   * 访问注释的原始值。 
   *  
   * @param 命名值名称。 
   *  
   * @param 值是实际值，其类型必须为{@link 字节}，{<@link>布尔值}，{<@link>字符}，{<@link>短}，{<@link> {@link 类型＃的整数，，{@link 浮点}，{@link 浮点}，{@link 双重}，{@link 字符串}或{@link 类型} OBJECT}或{@link  Type＃ARRAY}排序。 
   * 该值也可以是字节，布尔，短，字符，整数，长，浮点或双精度值的数组（这等效于使用{@link  #visitArray}并依次访问每个数组元素，但是更方便）。 
   * 
   */
  public void visit(final String name, final Object value) {
    if (av != null) {
      av.visit(name, value);
    }
  }

  /**
   * Visits an enumeration value of the annotation.
   *
   * @param name the value name.
   * @param descriptor the class descriptor of the enumeration class.
   * @param value the actual enumeration value.
   */
  /**
   * 访问注释的枚举值。 
   *  
   * @param 命名值名称。 
   *  
   * @param 描述符枚举类的类描述符。 
   *  
   * @param 值实际的枚举值。 
   * 
   */
  public void visitEnum(final String name, final String descriptor, final String value) {
    if (av != null) {
      av.visitEnum(name, descriptor, value);
    }
  }

  /**
   * Visits a nested annotation value of the annotation.
   *
   * @param name the value name.
   * @param descriptor the class descriptor of the nested annotation class.
   * @return a visitor to visit the actual nested annotation value, or {@literal null} if this
   *     visitor is not interested in visiting this nested annotation. <i>The nested annotation
   *     value must be fully visited before calling other methods on this annotation visitor</i>.
   */
  /**
   * 访问注释的嵌套注释值。 
   *  
   * @param 命名值名称。 
   *  
   * @param 描述符是嵌套注释类的类描述符。 
   *  
   * @return 访问者以访问实际的嵌套注释值，如果该访问者对访问此嵌套注释不感兴趣，则为{@literal null}。 
   *  <i>在此注释访问者上调用其他方法之前，必须完全访问嵌套的注释值</ i>。 
   * 
   */
  public AnnotationVisitor visitAnnotation(final String name, final String descriptor) {
    if (av != null) {
      return av.visitAnnotation(name, descriptor);
    }
    return null;
  }

  /**
   * Visits an array value of the annotation. Note that arrays of primitive types (such as byte,
   * boolean, short, char, int, long, float or double) can be passed as value to {@link #visit
   * visit}. This is what {@link ClassReader} does.
   *
   * @param name the value name.
   * @return a visitor to visit the actual array value elements, or {@literal null} if this visitor
   *     is not interested in visiting these values. The 'name' parameters passed to the methods of
   *     this visitor are ignored. <i>All the array values must be visited before calling other
   *     methods on this annotation visitor</i>.
   */
  /**
   * 访问注释的数组值。 
   * 请注意，原始类型的数组（例如byte，boolean，short，char，int，long，float或double）可以作为值传递给{@link  #visit visit}。 
   * 这就是{@link  ClassReader}所做的。 
   *  
   * @param 命名值名称。 
   *  
   * @return 访问者以访问实际的数组值元素，如果访问者对访问这些值不感兴趣，则为{@literal null}。 
   * 传递给此访问者方法的"名称"参数将被忽略。 
   *  <i>在此注释访问器上调用其他方法之前，必须先访问所有数组值</ i>。 
   * 
   */
  public AnnotationVisitor visitArray(final String name) {
    if (av != null) {
      return av.visitArray(name);
    }
    return null;
  }

  /** Visits the end of the annotation. */
  /**
   * 访问注释的末尾。 
   * 
   */
  public void visitEnd() {
    if (av != null) {
      av.visitEnd();
    }
  }
}
