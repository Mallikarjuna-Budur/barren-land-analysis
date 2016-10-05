package com.changent.barrenlandanalysis

/**
 * Created by worldwidewilly on 10/2/16.
 */
class BarrenLandParser {
    final Writer sysWriter

    public BarrenLandParser() {
        this(System.out)
    }

    public BarrenLandParser(OutputStream sysout) {
        sysWriter = new BufferedWriter(new OutputStreamWriter(sysout))
    }

    public List parseCommandLine(String... args) {

        if (noArgs(args)) {
            writeUsage()
            return null
        }

        def barrenSections = []

        for (arg in args) {
            def bounds = arg.split() as List
            def transformed = transform(bounds)
            if(validate(transformed)) {
                barrenSections << transformed
            }
            else {
                writeUsage()
                return null
            }
        }

        return barrenSections
    }

    private void writeUsage() {
        sysWriter.writeLine('Input error. Malformed section definition.')
        sysWriter.writeLine('')
        sysWriter.writeLine('usage: BarrenLandAnalysis <barren land definitions>')
        sysWriter.writeLine('')
        sysWriter.writeLine('BarrenLandAnalysis takes zero or more barren land definitions. Each is')
        sysWriter.writeLine('a rectangle defined as a string (i.e. enclosed within quotes), Each definition')
        sysWriter.writeLine('consists of four integers separated by single spaces, with no additional spaces')
        sysWriter.writeLine('in the string. The first two integers are the coordinates of the bottom left')
        sysWriter.writeLine('corner in the given rectangle, and the last two integers are the coordinates of')
        sysWriter.writeLine('the top right corner.')
        sysWriter.writeLine('')
        sysWriter.writeLine('If a barren land definition is outside the bounds of the land space, the parts')
        sysWriter.writeLine('of the definition exceeding that space is ignored.')
        sysWriter.writeLine('')
        sysWriter.writeLine('== EXAMPLES ==')
        sysWriter.writeLine('Example 1: A land space having no barren land:')
        sysWriter.writeLine('usage: BarrenLandAnalysis')
        sysWriter.writeLine('')
        sysWriter.writeLine('Example 2: A land space having one barren land definition:')
        sysWriter.writeLine('usage: BarrenLandAnalysis "0 292 399 307"')
        sysWriter.writeLine('')
        sysWriter.writeLine('Example 3: A land space having multiple barren land definitions:')
        sysWriter.writeLine('usage: BarrenLandAnalysis "48 192 351 207", "48 392 351 407"')

        sysWriter.flush()
    }

    private boolean noArgs(String... args) {
        !args || !args.length
    }

    private List transform(List bounds) {
        def transformed = []

        for(bound in bounds) {
            if (bound.isInteger()) {
                transformed << Integer.parseInt(bound)
            }
        }

        transformed
    }

    private boolean validate(List bounds) {
        if (bounds.size() != 4) {
            return false
        }
        true
    }
}
