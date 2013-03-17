/*
 * Copyright (c) 2011, Jonathan Keatley. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package spantable;

import java.util.EventListener;

public interface SpanListener extends EventListener {
	/**
	 * A Span was added to the SpanModel.
	 * 
	 * @param sme
	 *            The event
	 */
	void spanAdded(SpanEvent sme);

	/**
	 * A Span was removed from the SpanModel.
	 * 
	 * @param sme
	 *            The event
	 */
	void spanDeleted(SpanEvent sme);
}
