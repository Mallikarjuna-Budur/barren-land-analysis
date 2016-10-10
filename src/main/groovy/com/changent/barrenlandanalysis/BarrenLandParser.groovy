package com.changent.barrenlandanalysis

/**
 * Created by worldwidewilly on 10/2/16.
 */
class BarrenLandParser {

    public final static int LOWER_LEFT_ROW_IDX = 0
    public final static int LOWER_LEFT_COL_IDX = 1
    public final static int UPPER_RIGHT_ROW_IDX = 2
    public final static int UPPER_RIGHT_COL_IDX = 3

    static List parseCommandLine(String... args) {
        if (args == null) {
            return null
        }

        if (!args || !args.length) {
            return []
        }

        def barrenSections = (args as List).findResults { bounds ->
            def transformed = (bounds.split() as List).findResults { bound ->
                def trimmed = bound - ','
                trimmed.isInteger() ? Integer.parseInt(trimmed) : null
            }

            transformed.size() == 4 &&
                transformed[LOWER_LEFT_ROW_IDX] >= 0 &&
                transformed[LOWER_LEFT_COL_IDX] >= 0 &&
                transformed[UPPER_RIGHT_ROW_IDX] >= 0 &&
                transformed[UPPER_RIGHT_COL_IDX] >= 0 &&
                transformed[LOWER_LEFT_ROW_IDX] <= transformed[UPPER_RIGHT_ROW_IDX] &&
                transformed[LOWER_LEFT_COL_IDX] <= transformed[UPPER_RIGHT_COL_IDX] ?
                transformed : null
        }

        barrenSections ?: null
    }

}
