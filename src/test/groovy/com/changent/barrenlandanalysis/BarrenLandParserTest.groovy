package com.changent.barrenlandanalysis

import spock.lang.Specification

/**
 * Created by worldwidewilly on 10/2/16.
 */
class BarrenLandParserTest extends Specification {
    def "handles no barren land definitions"() {
        given:
        def args = [] as String[]
        def sysout = new ByteArrayOutputStream()
        BarrenLandParser barrenLandParser = new BarrenLandParser(sysout)
//        BarrenLandParser barrenLandParser = new BarrenLandParser()

        when:
        def result = barrenLandParser.parseCommandLine(args)

        then:
        result == null
        sysout.toString().contains 'Malformed section definition.'
    }

    def "handles one barren land definition"() {
        given:
        def args = ['0 292 399 307'] as String[]
        def sysout = new ByteArrayOutputStream()
        BarrenLandParser barrenLandParser = new BarrenLandParser(sysout)

        when:
        def result = barrenLandParser.parseCommandLine(args)

        then:
        result == [[0, 292, 399, 307]]
    }

    def "handles multiple barren land definitions without quotes"() {
        given:
        def args = ['48 192 351 207', '48 392 351 407', '120 52 135 547', '260 52 275 547'] as String[]
        def sysout = new ByteArrayOutputStream()
        BarrenLandParser barrenLandParser = new BarrenLandParser(sysout)

        when:
        def result = barrenLandParser.parseCommandLine(args)

        then:
        result == [[48, 192, 351, 207], [48, 392, 351, 407], [120, 52, 135, 547], [260, 52, 275, 547]]
    }

    def "handles definitions malformed with invalid non-numeric values"() {
        given:
        def args = ['0 292 threeNineNine 307'] as String[]
        def sysout = new ByteArrayOutputStream()
        BarrenLandParser barrenLandParser = new BarrenLandParser(sysout)

        when:
        def result = barrenLandParser.parseCommandLine(args)

        then:
        result == null
        sysout.toString().contains 'Malformed section definition.'
    }

    def "handles definitions malformed with incomplete boundaries"() {
        given:
        def args = ['0 292 307'] as String[]
        def sysout = new ByteArrayOutputStream()
        BarrenLandParser barrenLandParser = new BarrenLandParser(sysout)

        when:
        def result = barrenLandParser.parseCommandLine(args)

        then:
        result == null
        sysout.toString().contains 'Malformed section definition.'
    }
}
