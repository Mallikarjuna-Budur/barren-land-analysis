package com.changent.barrenlandanalysis

/**
 * Created by worldwidewilly on 10/2/16.
 */
class BarrenLandParser {

    public static List parseCommandLine(String... args) {

//        println "BarrenLandParser.parseCommandLine(${args})"

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
                transformed[0] >= 0 &&
                transformed[1] >= 0 &&
                transformed[2] >= 0 &&
                transformed[3] >= 0 &&
                transformed[0] <= transformed[2] &&
                transformed[1] <= transformed[3] ?
                transformed : null
        }

        return barrenSections ? barrenSections : null
    }

}
