package com.changent.barrenlandanalysis

import spock.lang.Specification

/**
 * Created by worldwidewilly on 10/2/16.
 */
class BarrenLandParserTest extends Specification {
    def "handles no barren land definitions"() {
        given:
        def args = [] as String[]

        when:
        def result = BarrenLandParser.parseCommandLine(args)

        then:
        result == []
    }

    def "handles one barren land definition"() {
        given:
        def args = ['0 292 399 307'] as String[]

        when:
        def result = BarrenLandParser.parseCommandLine(args)

        then:
        result == [[0, 292, 399, 307]]
    }

    def "handles multiple barren land definitions without quotes"() {
        given:
        def args = ['48 192 351 207', '48 392 351 407', '120 52 135 547', '260 52 275 547'] as String[]

        when:
        def result = BarrenLandParser.parseCommandLine(args)

        then:
        result == [[48, 192, 351, 207], [48, 392, 351, 407], [120, 52, 135, 547], [260, 52, 275, 547]]
    }

    def "handles multiple barren land definitions with trailing commas"() {
        given: "args the way the command processor interprets comma separated lists"
        def args = ['48 192 351 207,', '48 392 351 407,', '120 52 135 547,', '260 52 275 547,'] as String[]

        when:
        def result = BarrenLandParser.parseCommandLine(args)

        then:
        result == [[48, 192, 351, 207], [48, 392, 351, 407], [120, 52, 135, 547], [260, 52, 275, 547]]
    }

    def "handles definitions malformed with invalid non-numeric values"() {
        given:
        def args = ['0 292 threeNineNine 307'] as String[]

        when:
        def result = BarrenLandParser.parseCommandLine(args)

        then:
        result == null
    }

    def "handles definitions malformed with incomplete boundaries"() {
        given:
        def args = ['0 292 307'] as String[]

        when:
        def result = BarrenLandParser.parseCommandLine(args)

        then:
        result == null
    }

    def "handles definitions malformed with negative boundaries"() {

        expect:
        result == BarrenLandParser.parseCommandLine(args)

        where:
        args                           | result
        ['-1 292 399 307'] as String[] | null
        ['1 -292 399 307'] as String[] | null
        ['1 292 -399 307'] as String[] | null
        ['1 292 399 -307'] as String[] | null
    }

    def "handles definitions malformed with upper right coordinate less than lower left"() {

        expect:
        result == BarrenLandParser.parseCommandLine(args)

        where:
        args                         | result
        ['10 292 1 307'] as String[] | null
        ['10 292 399 1'] as String[] | null
    }
}
