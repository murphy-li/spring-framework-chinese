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
 * A visitor to visit a Java class. The methods of this class must be called in the following order:
 * {@code visit} [ {@code visitSource} ] [ {@code visitModule} ][ {@code visitNestHost} ][ {@code
 * visitPermittedSubtype} ][ {@code visitOuterClass} ] ( {@code visitAnnotation} | {@code
 * visitTypeAnnotation} | {@code visitAttribute} )* ( {@code visitNestMember} | {@code
 * visitInnerClass} | {@code visitField} | {@code visitMethod} )* {@code visitEnd}.
 *
 * @author Eric Bruneton
 */
/**
 * 访问Java类的访问者。 
 * 此类的方法必须按以下顺序调用：{@code  visit} [{@code  visitSource}] [{@code  visitModule}] [{@code  visitNestHost}] [{@code  visitPermittedSubtype}] [{@code  visitOuterClass}]（{@code  visitAnnotation} | {@code  visitTypeAnnotation} | {@code  visitAttribute}）（{@code  visitNestMember} | { @code  visitInnerClass} | {@code  visitField} | {@code  visitMethod}）{@code  visitEnd}。 
 *  @author 埃里克·布鲁内顿
 */
public abstract class ClassVisitor {

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

  /** The class visitor to which this visitor must delegate method calls. May be {@literal null}. */
  /**
   * 该访问者必须委派方法调用的类访问者。 
   * 可能为{@literal null}。 
   * 
   */
  protected ClassVisitor cv;

  /**
   * Constructs a new {@link ClassVisitor}.
   *
   * @param api the ASM API version implemented by this visitor. Must be one of {@link
   *     Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
   */
  /**
   * 构造一个新的{@link  ClassVisitor}。 
   *  
   * @param  api此访客实现的ASM API版本。 
   * 必须是{@link  Opcodes＃ASM4}，{<@link> Opcodes＃ASM5}，{<@link> Opcodes＃ASM6}或{@link  Opcodes＃ASM7}之一。 
   * 
   */
  public ClassVisitor(final int api) {
    this(api, null);
  }

  /**
   * Constructs a new {@link ClassVisitor}.
   *
   * @param api the ASM API version implemented by this visitor. Must be one of {@link
   *     Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
   * @param classVisitor the class visitor to which this visitor must delegate method calls. May be
   *     null.
   */
  /**
   * 构造一个新的{@link  ClassVisitor}。 
   *  
   * @param  api此访客实现的ASM API版本。 
   * 必须是{@link  Opcodes＃ASM4}，{<@link> Opcodes＃ASM5}，{<@link> Opcodes＃ASM6}或{@link  Opcodes＃ASM7}之一。 
   *  
   * @param  classVisitor该访问者必须委派方法调用的类访问者。 
   * 可能为空。 
   * 
   */
  @SuppressWarnings("deprecation")
  public ClassVisitor(final int api, final ClassVisitor classVisitor) {
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
    this.cv = classVisitor;
  }

  /**
   * Visits the header of the class.
   *
   * @param version the class version. The minor version is stored in the 16 most significant bits,
   *     and the major version in the 16 least significant bits.
   * @param access the class's access flags (see {@link Opcodes}). This parameter also indicates if
   *     the class is deprecated.
   * @param name the internal name of the class (see {@link Type#getInternalName()}).
   * @param signature the signature of this class. May be {@literal null} if the class is not a
   *     generic one, and does not extend or implement generic classes or interfaces.
   * @param superName the internal of name of the super class (see {@link Type#getInternalName()}).
   *     For interfaces, the super class is {@link Object}. May be {@literal null}, but only for the
   *     {@link Object} class.
   * @param interfaces the internal names of the class's interfaces (see {@link
   *     Type#getInternalName()}). May be {@literal null}.
   */
  /**
   * 访问类的标题。 
   *  
   * @param 版本类的版本。 
   * 次要版本存储在16个最高有效位中，而主版本存储在16个最低有效位中。 
   *  
   * @param 访问类的访问标志（请参见{@link 操作码}）。 
   * 此参数还指示是否弃用该类。 
   *  
   * @param 命名类的内部名称（请参见{@link  Type＃getInternalName（）}）。 
   *  
   * @param 签名此类的签名。 
   * 如果该类不是通用类，并且不扩展或实现通用类或接口，则可以为{@literal null}。 
   *  
   * @param  superName超类名称的内部（请参见{@link  Type＃getInternalName（）}）。 
   * 对于接口，超类为{@link  Object}。 
   * 可以为{@literal null}，但仅用于{@link  Object}类。 
   *  
   * @param 接口类的接口的内部名称（请参见{@link  Type＃getInternalName（）}）。 
   * 可能为{@literal null}。 
   * 
   */
  public void visit(
      final int version,
      final int access,
      final String name,
      final String signature,
      final String superName,
      final String[] interfaces) {
    if (cv != null) {
      cv.visit(version, access, name, signature, superName, interfaces);
    }
  }

  /**
   * Visits the source of the class.
   *
   * @param source the name of the source file from which the class was compiled. May be {@literal
   *     null}.
   * @param debug additional debug information to compute the correspondence between source and
   *     compiled elements of the class. May be {@literal null}.
   */
  /**
   * 访问课程的源代码。 
   *  
   * @param  source从中编译类的源文件的名称。 
   * 可能为{@literal null}。 
   *  
   * @param 调试其他调试信息，以计算该类的源元素和已编译元素之间的对应关系。 
   * 可能为{@literal null}。 
   * 
   */
  public void visitSource(final String source, final String debug) {
    if (cv != null) {
      cv.visitSource(source, debug);
    }
  }

  /**
   * Visit the module corresponding to the class.
   *
   * @param name the fully qualified name (using dots) of the module.
   * @param access the module access flags, among {@code ACC_OPEN}, {@code ACC_SYNTHETIC} and {@code
   *     ACC_MANDATED}.
   * @param version the module version, or {@literal null}.
   * @return a visitor to visit the module values, or {@literal null} if this visitor is not
   *     interested in visiting this module.
   */
  /**
   * 访问与课程相对应的模块。 
   *  
   * @param 命名模块的完全限定名称（使用点）。 
   *  
   * @param 访问{@code  ACC_OPEN}，{<@code> ACC_SYNTHETIC}和{@code  ACC_MANDATED}中的模块访问标志。 
   *  
   * @param 版本模块版本，或{@literal null}。 
   *  
   * @return 访问者以访问模块值，如果访问者对访问此模块不感兴趣，则为{@literal null}。 
   * 
   */
  public ModuleVisitor visitModule(final String name, final int access, final String version) {
    if (api < Opcodes.ASM6) {
      throw new UnsupportedOperationException("This feature requires ASM6");
    }
    if (cv != null) {
      return cv.visitModule(name, access, version);
    }
    return null;
  }

  /**
   * Visits the nest host class of the class. A nest is a set of classes of the same package that
   * share access to their private members. One of these classes, called the host, lists the other
   * members of the nest, which in turn should link to the host of their nest. This method must be
   * called only once and only if the visited class is a non-host member of a nest. A class is
   * implicitly its own nest, so it's invalid to call this method with the visited class name as
   * argument.
   *
   * @param nestHost the internal name of the host class of the nest.
   */
  /**
   * 访问该类的嵌套宿主类。 
   * 嵌套是同一包的一组类的集合，这些类共享对其私有成员的访问。 
   * 这些类之一称为宿主，列出了嵌套的其他成员，而后者又应链接到其嵌套的宿主。 
   * 仅当被访问的类是嵌套的非宿主成员时，才必须调用此方法一次。 
   * 一个类隐式地是其自己的嵌套，因此以访问的类名作为参数来调用此方法是无效的。 
   *  
   * @param  nestHost嵌套主机类的内部名称。 
   * 
   */
  public void visitNestHost(final String nestHost) {
    if (api < Opcodes.ASM7) {
      throw new UnsupportedOperationException("This feature requires ASM7");
    }
    if (cv != null) {
      cv.visitNestHost(nestHost);
    }
  }

  /**
   * Visits the enclosing class of the class. This method must be called only if the class has an
   * enclosing class.
   *
   * @param owner internal name of the enclosing class of the class.
   * @param name the name of the method that contains the class, or {@literal null} if the class is
   *     not enclosed in a method of its enclosing class.
   * @param descriptor the descriptor of the method that contains the class, or {@literal null} if
   *     the class is not enclosed in a method of its enclosing class.
   */
  /**
   * 访问该类的封闭类。 
   * 仅当类具有封闭的类时，才必须调用此方法。 
   *  
   * @param 所属类的所有者内部名称。 
   *  
   * @param 命名包含该类的方法的名称； 
   * 如果该类未包含在其封闭类的方法中，则为{@literal null}。 
   *  
   * @param 描述符包含该类的方法的描述符，如果该类未包含在其封闭类的方法中，则为{@literal null}。 
   * 
   */
  public void visitOuterClass(final String owner, final String name, final String descriptor) {
    if (cv != null) {
      cv.visitOuterClass(owner, name, descriptor);
    }
  }

  /**
   * Visits an annotation of the class.
   *
   * @param descriptor the class descriptor of the annotation class.
   * @param visible {@literal true} if the annotation is visible at runtime.
   * @return a visitor to visit the annotation values, or {@literal null} if this visitor is not
   *     interested in visiting this annotation.
   */
  /**
   * 访问该类的注释。 
   *  
   * @param 描述符注释类的类描述符。 
   *  
   * @param 可见{@literal true}（如果注释在运行时可见）。 
   *  
   * @return 访问者以访问注释值，如果访问者对访问此注释不感兴趣，则返回{@literal null}。 
   * 
   */
  public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
    if (cv != null) {
      return cv.visitAnnotation(descriptor, visible);
    }
    return null;
  }

  /**
   * Visits an annotation on a type in the class signature.
   *
   * @param typeRef a reference to the annotated type. The sort of this type reference must be
   *     {@link TypeReference#CLASS_TYPE_PARAMETER}, {@link
   *     TypeReference#CLASS_TYPE_PARAMETER_BOUND} or {@link TypeReference#CLASS_EXTENDS}. See
   *     {@link TypeReference}.
   * @param typePath the path to the annotated type argument, wildcard bound, array element type, or
   *     static inner type within 'typeRef'. May be {@literal null} if the annotation targets
   *     'typeRef' as a whole.
   * @param descriptor the class descriptor of the annotation class.
   * @param visible {@literal true} if the annotation is visible at runtime.
   * @return a visitor to visit the annotation values, or {@literal null} if this visitor is not
   *     interested in visiting this annotation.
   */
  /**
   * 在类签名中访问有关类型的注释。 
   *  
   * @param  typeRef对带注释类型的引用。 
   * 此类型引用的类型必须为{@link  TypeReference＃CLASS_TYPE_PARAMETER}，{<@link> TypeReference＃CLASS_TYPE_PARAMETER_BOUND}或{@link  TypeReference＃CLASS_EXTENDS}。 
   * 请参阅{@link  TypeReference}。 
   *  
   * @param  typePath注释类型参数，通配符绑定，数组元素类型或'typeRef'中的静态内部类型的路径。 
   * 如果注释整体上针对"typeRef"，则可能为{@literal null}。 
   *  
   * @param 描述符注释类的类描述符。 
   *  
   * @param 可见{@literal true}（如果注释在运行时可见）。 
   *  
   * @return 访问者以访问注释值，如果访问者对访问此注释不感兴趣，则返回{@literal null}。 
   * 
   */
  public AnnotationVisitor visitTypeAnnotation(
      final int typeRef, final TypePath typePath, final String descriptor, final boolean visible) {
    if (api < Opcodes.ASM5) {
      throw new UnsupportedOperationException("This feature requires ASM5");
    }
    if (cv != null) {
      return cv.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }
    return null;
  }

  /**
   * Visits a non standard attribute of the class.
   *
   * @param attribute an attribute.
   */
  /**
   * 访问类的非标准属性。 
   *  
   * @param 属性属性。 
   * 
   */
  public void visitAttribute(final Attribute attribute) {
    if (cv != null) {
      cv.visitAttribute(attribute);
    }
  }

  /**
   * Visits a member of the nest. A nest is a set of classes of the same package that share access
   * to their private members. One of these classes, called the host, lists the other members of the
   * nest, which in turn should link to the host of their nest. This method must be called only if
   * the visited class is the host of a nest. A nest host is implicitly a member of its own nest, so
   * it's invalid to call this method with the visited class name as argument.
   *
   * @param nestMember the internal name of a nest member.
   */
  /**
   * 造访巢穴成员。 
   * 嵌套是同一包的一组类的集合，这些类共享对其私有成员的访问。 
   * 这些类之一称为宿主，列出了嵌套的其他成员，而后者又应链接到其嵌套的宿主。 
   * 仅当访问的类是嵌套的宿主时，才必须调用此方法。 
   * 嵌套主机隐式是其自身嵌套的成员，因此以访问的类名作为参数来调用此方法是无效的。 
   *  
   * @param  nestMember嵌套成员的内部名称。 
   * 
   */
  public void visitNestMember(final String nestMember) {
    if (api < Opcodes.ASM7) {
      throw new UnsupportedOperationException("This feature requires ASM7");
    }
    if (cv != null) {
      cv.visitNestMember(nestMember);
    }
  }

  /**
   * <b>Experimental, use at your own risk. This method will be renamed when it becomes stable, this
   * will break existing code using it</b>. Visits a permitted subtypes. A permitted subtypes is one
   * of the allowed subtypes of the current class.
   *
   * @param permittedSubtype the internal name of a permitted subtype.
   * @deprecated this API is experimental.
   */
  /**
   * <b>实验性使用，后果自负。 
   * 该方法变得稳定后将被重命名，这将破坏使用它的现有代码</ b>。 
   * 访问允许的子类型。 
   * 允许的子类型是当前类的允许的子类型之一。 
   *  
   * @param  allowedSubtype允许的子类型的内部名称。 
   *  @不推荐使用此API是实验性的。 
   * 
   */
  @Deprecated
  public void visitPermittedSubtypeExperimental(final String permittedSubtype) {
    if (api != Opcodes.ASM8_EXPERIMENTAL) {
      throw new UnsupportedOperationException("This feature requires ASM8_EXPERIMENTAL");
    }
    if (cv != null) {
      cv.visitPermittedSubtypeExperimental(permittedSubtype);
    }
  }

  /**
   * Visits information about an inner class. This inner class is not necessarily a member of the
   * class being visited.
   *
   * @param name the internal name of an inner class (see {@link Type#getInternalName()}).
   * @param outerName the internal name of the class to which the inner class belongs (see {@link
   *     Type#getInternalName()}). May be {@literal null} for not member classes.
   * @param innerName the (simple) name of the inner class inside its enclosing class. May be
   *     {@literal null} for anonymous inner classes.
   * @param access the access flags of the inner class as originally declared in the enclosing
   *     class.
   */
  /**
   * 访问有关内部类的信息。 
   * 此内部类不一定是要访问的类的成员。 
   *  
   * @param 命名内部类的内部名称（请参见{@link  Type＃getInternalName（）}）。 
   *  
   * @param  externalName内部类所属的类的内部名称（请参见{@link  Type＃getInternalName（）}）。 
   * 对于非成员类，可能为{@literal null}。 
   *  
   * @param  innerName内部类在其封闭类内的（简单）名称。 
   * 对于匿名内部类，可以为{@literal null}。 
   *  
   * @param 访问内部类的访问标志，如最初在封闭类中声明的那样。 
   * 
   */
  public void visitInnerClass(
      final String name, final String outerName, final String innerName, final int access) {
    if (cv != null) {
      cv.visitInnerClass(name, outerName, innerName, access);
    }
  }

  /**
   * Visits a record component of the class.
   *
   * @param access the record component access flags, the only possible value is {@link
   *     Opcodes#ACC_DEPRECATED}.
   * @param name the record component name.
   * @param descriptor the record component descriptor (see {@link Type}).
   * @param signature the record component signature. May be {@literal null} if the record component
   *     type does not use generic types.
   * @return a visitor to visit this record component annotations and attributes, or {@literal null}
   *     if this class visitor is not interested in visiting these annotations and attributes.
   * @deprecated this API is experimental.
   */
  /**
   * 访问该类的记录组件。 
   *  
   * @param 访问记录组件访问标志，唯一可能的值为{@link  Opcodes＃ACC_DEPRECATED}。 
   *  
   * @param 命名记录组件名称。 
   *  
   * @param 描述符是记录组件描述符（请参见{@link 类型}）。 
   *  
   * @param 签名记录组件的签名。 
   * 如果记录组件类型不使用泛型类型，则可以为{@literal null}。 
   *  
   * @return 访问者，以访问此记录组件的注释和属性； 
   * 如果此类访问者对访问这些注释和属性不感兴趣，则为{@literal null}。 
   *  @不推荐使用此API是实验性的。 
   * 
   */
  @Deprecated
  public RecordComponentVisitor visitRecordComponentExperimental(
      final int access, final String name, final String descriptor, final String signature) {
    if (api < Opcodes.ASM8_EXPERIMENTAL) {
      throw new UnsupportedOperationException("This feature requires ASM8_EXPERIMENTAL");
    }
    if (cv != null) {
      return cv.visitRecordComponentExperimental(access, name, descriptor, signature);
    }
    return null;
  }

  /**
   * Visits a field of the class.
   *
   * @param access the field's access flags (see {@link Opcodes}). This parameter also indicates if
   *     the field is synthetic and/or deprecated.
   * @param name the field's name.
   * @param descriptor the field's descriptor (see {@link Type}).
   * @param signature the field's signature. May be {@literal null} if the field's type does not use
   *     generic types.
   * @param value the field's initial value. This parameter, which may be {@literal null} if the
   *     field does not have an initial value, must be an {@link Integer}, a {@link Float}, a {@link
   *     Long}, a {@link Double} or a {@link String} (for {@code int}, {@code float}, {@code long}
   *     or {@code String} fields respectively). <i>This parameter is only used for static
   *     fields</i>. Its value is ignored for non static fields, which must be initialized through
   *     bytecode instructions in constructors or methods.
   * @return a visitor to visit field annotations and attributes, or {@literal null} if this class
   *     visitor is not interested in visiting these annotations and attributes.
   */
  /**
   * 访问课程的某个领域。 
   *  
   * @param 访问该字段的访问标志（请参见{@link 操作码}）。 
   * 此参数还指示该字段是合成字段还是不建议使用的字段。 
   *  
   * @param 命名字段的名称。 
   *  
   * @param 描述符字段的描述符（请参见{@link 类型}）。 
   *  
   * @param 签名字段的签名。 
   * 如果字段的类型不使用泛型类型，则可以为{@literal null}。 
   *  
   * @param 值字段的初始值。 
   * 如果该字段没有初始值，则该参数可以为{@literal null}，该参数必须为{@link  Integer}，{<@link> Float}，{<@link> Long}， {@link  Double}或{@link  String}（用于{@code  int}，{<@code> float}，{<@code> long}或{@code  String}字段）。 
   *  <i>此参数仅用于静态字段</ i>。 
   * 对于非静态字段，将忽略其值，该字段必须通过构造函数或方法中的字节码指令进行初始化。 
   *  
   * @return 访问者以访问字段注释和属性，如果该类访问者对访问这些注释和属性不感兴趣，则为{@literal null}。 
   * 
   */
  public FieldVisitor visitField(
      final int access,
      final String name,
      final String descriptor,
      final String signature,
      final Object value) {
    if (cv != null) {
      return cv.visitField(access, name, descriptor, signature, value);
    }
    return null;
  }

  /**
   * Visits a method of the class. This method <i>must</i> return a new {@link MethodVisitor}
   * instance (or {@literal null}) each time it is called, i.e., it should not return a previously
   * returned visitor.
   *
   * @param access the method's access flags (see {@link Opcodes}). This parameter also indicates if
   *     the method is synthetic and/or deprecated.
   * @param name the method's name.
   * @param descriptor the method's descriptor (see {@link Type}).
   * @param signature the method's signature. May be {@literal null} if the method parameters,
   *     return type and exceptions do not use generic types.
   * @param exceptions the internal names of the method's exception classes (see {@link
   *     Type#getInternalName()}). May be {@literal null}.
   * @return an object to visit the byte code of the method, or {@literal null} if this class
   *     visitor is not interested in visiting the code of this method.
   */
  /**
   * 访问类的方法。 
   * 每次被调用时，此方法<i>必须</ i>返回一个新的{@link  MethodVisitor}实例（或{@literal null}），即，它不应返回以前返回的访问者。 
   *  
   * @param 访问方法的访问标志（请参见{@link 操作码}）。 
   * 此参数还指示该方法是否为合成方法和/或已弃用。 
   *  
   * @param 命名方法的名称。 
   *  
   * @param 描述符方法的描述符（请参见{@link 类型}）。 
   *  
   * @param 签名方法的签名。 
   * 如果方法参数，返回类型和异常未使用泛型类型，则可能为{@literal null}。 
   *  
   * @param 异常方法的异常类的内部名称（请参见{@link  Type＃getInternalName（）}）。 
   * 可能为{@literal null}。 
   *  
   * @return 一个对象，用于访问该方法的字节码，如果该类访问者对访问此方法的代码不感兴趣，则返回{@literal null}。 
   * 
   */
  public MethodVisitor visitMethod(
      final int access,
      final String name,
      final String descriptor,
      final String signature,
      final String[] exceptions) {
    if (cv != null) {
      return cv.visitMethod(access, name, descriptor, signature, exceptions);
    }
    return null;
  }

  /**
   * Visits the end of the class. This method, which is the last one to be called, is used to inform
   * the visitor that all the fields and methods of the class have been visited.
   */
  /**
   * 参观课程结束。 
   * 此方法是最后一个要调用的方法，用于通知访问者该类的所有字段和方法均已被访问。 
   * 
   */
  public void visitEnd() {
    if (cv != null) {
      cv.visitEnd();
    }
  }
}
