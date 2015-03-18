/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javaq.chartfaces.facelets;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodNotFoundException;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

/**
 * Optional Rule for binding Method[Binding|Expression] properties
 * 
 * @author Mike Kienenberger
 * @author Jacob Hookom
 * 
 *         Implementation copied from Facelets 1.1.14, as it got hidden by JSF
 *         2.0
 */
@SuppressWarnings("deprecation")
public class MethodRule extends MetaRule {

	private static class LegacyMethodBinding extends MethodBinding implements
			Serializable {

		private static final long serialVersionUID = 1L;

		private final MethodExpression m;

		public LegacyMethodBinding(final MethodExpression m) {
			this.m = m;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.faces.el.MethodBinding#getType(javax.faces.context.FacesContext
		 * )
		 */

		@Override
		public String getExpressionString() {
			return this.m.getExpressionString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.faces.el.MethodBinding#invoke(javax.faces.context.FacesContext,
		 * java.lang.Object[])
		 */

		@Override
		public Class<?> getType(final FacesContext context) {
			try {
				return this.m.getMethodInfo(context.getELContext()).getReturnType();
			} catch (final javax.el.MethodNotFoundException e) {
				throw new MethodNotFoundException(e.getMessage(), e);
			} catch (final ELException e) {
				throw new ELException(e.getMessage(), e);
			}
		}

		@Override
		public Object invoke(final FacesContext context, final Object[] params) {
			try {
				return this.m.invoke(context.getELContext(), params);
			} catch (final javax.el.MethodNotFoundException e) {
				throw new MethodNotFoundException(e.getMessage(), e);
			} catch (final ELException e) {
				throw new ELException(e.getMessage(), e);
			}
		}
	}

	private static class MethodBindingMetadata extends Metadata {
		private final TagAttribute attribute;

		private final Method method;

		private final Class<?>[] paramList;

		private final Class<?> returnType;

		public MethodBindingMetadata(final Method m, final TagAttribute attrib,
										final Class<?> type,
				final Class<?>[] pList) {
			this.method = m;
			this.attribute = attrib;
			this.paramList = Arrays.copyOf(pList, pList.length);
			this.returnType = type;
		}

		@Override
		public void applyMetadata(final FaceletContext ctx, final Object instance) {
			final MethodExpression expr =
					this.attribute
							.getMethodExpression(ctx, this.returnType, this.paramList);

			try {
				this.method.invoke(instance,
								new Object[] { new LegacyMethodBinding(expr) });
			} catch (final InvocationTargetException e) {
				throw new TagAttributeException(this.attribute, e);
			} catch (final Exception e) {
				throw new TagAttributeException(this.attribute, e);
			}
		}
	}

	private static class MethodExpressionMetadata extends Metadata {
		private final TagAttribute attribute;

		private final Method method;

		private final Class<?>[] paramList;

		private final Class<?> returnType;

		public MethodExpressionMetadata(final Method m, final TagAttribute attrib,
				final Class<?> type, final Class<?>[] pList) {
			this.method = m;
			this.attribute = attrib;
			this.paramList = Arrays.copyOf(pList, pList.length);
			this.returnType = type;
		}

		@Override
		public void applyMetadata(final FaceletContext ctx, final Object instance) {
			final MethodExpression expr =
					this.attribute
							.getMethodExpression(ctx, this.returnType, this.paramList);

			try {
				this.method.invoke(instance, new Object[] { expr });
			} catch (final InvocationTargetException e) {
				throw new TagAttributeException(this.attribute, e);
			} catch (final Exception e) {
				throw new TagAttributeException(this.attribute, e);
			}
		}
	}

	private final String methodName;

	private final Class<?>[] params;

	private final Class<?> returnTypeClass;

	public MethodRule(final String name, final Class<?> typeClass,
						final Class<?>[] parameters) {
		this.methodName = name;
		this.returnTypeClass = typeClass;
		this.params = Arrays.copyOf(parameters, parameters.length);
	}

	@Override
	public Metadata applyRule(final String name, final TagAttribute attribute,
								final MetadataTarget meta) {
		if (!name.equals(this.methodName)) {
			return null;
		}

		if (MethodBinding.class.equals(meta.getPropertyType(name))) {
			final Method method = meta.getWriteMethod(name);
			if (method != null) {
				return new MethodBindingMetadata(method, attribute,
													this.returnTypeClass,
													this.params);
			}
		} else if (MethodExpression.class.equals(meta.getPropertyType(name))) {
			final Method method = meta.getWriteMethod(name);
			if (method != null) {
				return new MethodExpressionMetadata(method, attribute,
													this.returnTypeClass,
													this.params);
			}
		}

		return null;
	}
}
