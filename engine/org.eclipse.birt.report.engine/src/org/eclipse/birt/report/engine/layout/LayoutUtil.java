/***********************************************************************
 * Copyright (c) 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.layout;

import org.eclipse.birt.report.engine.content.IBandContent;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IGroupContent;
import org.eclipse.birt.report.engine.content.IRowContent;
import org.eclipse.birt.report.engine.content.IStyle;
import org.eclipse.birt.report.engine.content.ITableContent;
import org.eclipse.birt.report.engine.css.engine.value.birt.BIRTConstants;

public class LayoutUtil
{

	public static boolean isRowHidden( Object rowContent, String format )
	{
		if ( rowContent != null && rowContent instanceof IRowContent )
		{
			IStyle style = ( (IRowContent) rowContent ).getStyle( );
			if(IStyle.NONE_VALUE.equals(style.getProperty( IStyle.STYLE_DISPLAY )))
			{
				return true;
			}
			String formats = style.getVisibleFormat( );
			if ( formats != null
					&& ( formats.indexOf( format ) >= 0 || formats
							.indexOf( BIRTConstants.BIRT_ALL_VALUE ) >= 0 ) )
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isRepeatableRow( IRowContent row )
	{
		IContent parent = (IContent) row.getParent( );
		if ( parent != null && ( parent instanceof IBandContent ) )
		{
			IBandContent band = (IBandContent) parent;
			int type = band.getBandType( );
			if ( type == IBandContent.BAND_HEADER )
			{
				IContent pp = (IContent) band.getParent( );
				if ( pp != null && pp instanceof ITableContent )
				{
					ITableContent table = (ITableContent) band.getParent( );
					return table.isHeaderRepeat( );
				}
			}
			else if ( type == IBandContent.BAND_GROUP_HEADER )
			{
				IContent pp = (IContent) band.getParent( );
				if ( pp != null && pp instanceof IGroupContent )
				{
					IGroupContent group = (IGroupContent) band.getParent( );
					return group.isHeaderRepeat( );
				}
			}

		}
		return false;
	}
}
