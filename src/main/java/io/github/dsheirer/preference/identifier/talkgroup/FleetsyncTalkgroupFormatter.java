/*
 * ******************************************************************************
 * sdrtrunk
 * Copyright (C) 2014-2018 Dennis Sheirer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * *****************************************************************************
 */

package io.github.dsheirer.preference.identifier.talkgroup;

import io.github.dsheirer.identifier.talkgroup.TalkgroupIdentifier;
import io.github.dsheirer.module.decode.fleetsync2.identifier.FleetsyncIdentifier;
import io.github.dsheirer.preference.identifier.IntegerFormat;

/**
 * Formats Fleetsync 20-bit identifiers into either an integer value or an 8-bit fleet and 12-bit ident
 */
public class FleetsyncTalkgroupFormatter extends IntegerFormatter
{
    private static final int IDENTIFIER_DECIMAL_WIDTH = 7;
    private static final int IDENTIFIER_HEXADECIMAL_WIDTH = 5;
    private static final int FLEET_DECIMAL_WIDTH = 3;
    private static final int IDENT_DECIMAL_WIDTH = 4;
    private static final String SEPARATOR = "-";

    /**
     * Formats the individual or group identifier to the specified format and width.
     */
    public static String format(TalkgroupIdentifier identifier, IntegerFormat format, boolean fixedWidth)
    {
        if(identifier instanceof FleetsyncIdentifier)
        {
            FleetsyncIdentifier fleetsync = (FleetsyncIdentifier)identifier;

            if(fixedWidth)
            {
                switch(format)
                {
                    case DECIMAL:
                        return toDecimal(fleetsync.getValue(), IDENTIFIER_DECIMAL_WIDTH);
                    case FORMATTED:
                        return toDecimal(fleetsync.getFleet(), FLEET_DECIMAL_WIDTH) + SEPARATOR +
                            toDecimal(fleetsync.getIdent(), IDENT_DECIMAL_WIDTH);
                    case HEXADECIMAL:
                        return toHex(fleetsync.getValue(), IDENTIFIER_HEXADECIMAL_WIDTH);
                    default:
                        throw new IllegalArgumentException("Unrecognized integer format: " + format);
                }
            }
            else
            {
                switch(format)
                {
                    case DECIMAL:
                        return fleetsync.toString();
                    case FORMATTED:
                        return fleetsync.getFleet() + SEPARATOR + fleetsync.getIdent();
                    case HEXADECIMAL:
                        return toHex(identifier.getValue());
                    default:
                        throw new IllegalArgumentException("Unrecognized integer format: " + format);
                }
            }
        }

        return identifier.toString();
    }
}
