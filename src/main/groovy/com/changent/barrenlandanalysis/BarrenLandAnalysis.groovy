package com.changent.barrenlandanalysis

/**
 * BarrenLandAnalysis
 *
 * A tool demonstrating Breadth First Search (BFS). The main
 * crux of the algorithm is in method caclulateArableSections().
 *
 *  @author worldwidewilly, @date 10/1/16 7:54 AM
 */
class BarrenLandAnalysis {
    private final int widthLowerBound = 0
    private final int heightLowerBound = 0
    private int widthUpperBound
    private int heightUpperBound
    int width = 400
    int height = 600
    Writer sysout = new BufferedWriter(new OutputStreamWriter(System.out))

    String run(String... args) {

        def barrenSections = BarrenLandParser.parseCommandLine(args)

        if (barrenSections == null) {
            writeUsage('Input error. Malformed section definition.')
            return null
        }

        widthUpperBound = width - 1
        heightUpperBound = height - 1

        def parcel = []
        (0..widthUpperBound).each { x ->
            def squares = []
            (0..heightUpperBound).each { y ->
                squares << 0
            }
            parcel << squares
        }

        def markedParcel = markOffBarrenSections(parcel, barrenSections)

        def arableLand = caclulateArableSections(markedParcel)

        arableLand ? arableLand.join(' ') : '0'

    }

    def markOffBarrenSections(parcel, barrenSections) {

        for (barrenSection in barrenSections) {
            def minX = barrenSection[BarrenLandParser.LOWER_LEFT_ROW_IDX]
            def minY = barrenSection[BarrenLandParser.LOWER_LEFT_COL_IDX]
            def maxX = barrenSection[BarrenLandParser.UPPER_RIGHT_ROW_IDX] <= width ?
                    barrenSection[BarrenLandParser.UPPER_RIGHT_ROW_IDX] + 1 : width
            def maxY = barrenSection[BarrenLandParser.UPPER_RIGHT_COL_IDX] <= height ?
                    barrenSection[BarrenLandParser.UPPER_RIGHT_COL_IDX] + 1 : height
            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    parcel[x][y] = -1
                }
            }
        }

        parcel
    }

    private List caclulateArableSections(parcel) {

        def currentSection = 0
        def sectionMap = [:]
        def queue = [] as LinkedList
        def node = []
        def x = 0
        def y = 0

        while (x < width && y < height) {
            if (queue.isEmpty()) {

                node << x << y

                if (isFirstVisitToNode(x, y, parcel)) {
                    currentSection++
                    sectionMap[currentSection] = 0
                    queue << [x, y]
                }

                def indices = incrementIndices(x, y)
                x = indices.first
                y = indices.second
            }
            if (!queue.isEmpty()) {
                node = queue.pop()

                def x1 = node[0]
                def y1 = node[1]

                if (isFirstVisitToNode(x1, y1, parcel)) {
                    addUnvisitedNeighborsToQueue(x1, y1, parcel, queue)

                    parcel[x1][y1] = currentSection

                    sectionMap[currentSection] = sectionMap[currentSection] + 1
                }
            }
        }

        sectionMap.values().sort()
    }

    private Tuple2 incrementIndices(x, y) {
        def tuple

        if (x == widthUpperBound) {
            tuple = new Tuple2(0, y + 1)
        }
        else {
            tuple = new Tuple2(x + 1, y)
        }

        tuple
    }

    private boolean isFirstVisitToNode(x, y, parcel) {
        parcel[x][y] == 0
    }

    private void addUnvisitedNeighborsToQueue(x, y, parcel, queue) {
        if (x > widthLowerBound) {
            addUnvisitedNeighborToQueue(x - 1, y, queue, parcel)
        }
        if (x < widthUpperBound) {
            addUnvisitedNeighborToQueue(x + 1, y, queue, parcel)
        }
        if (y > heightLowerBound) {
            addUnvisitedNeighborToQueue(x, y - 1, queue, parcel)
        }
        if (y < heightUpperBound) {
            addUnvisitedNeighborToQueue(x, y + 1, queue, parcel)
        }
    }

    private List addUnvisitedNeighborToQueue(x, y, queue, parcel) {
        if (isFirstVisitToNode(x, y, parcel)) {
            queue << [x, y]
        }
    }

    private void writeUsage(userMessage) {
        if (userMessage) {
            sysout.writeLine userMessage
            sysout.writeLine ''
        }

        sysout.writeLine 'usage: BarrenLandAnalysis <barren land definitions>'
        sysout.writeLine ''
        sysout.writeLine 'BarrenLandAnalysis takes zero or more barren land definitions. Each is'
        sysout.writeLine 'a rectangle defined as a string (i.e. enclosed within quotes , Each definition'
        sysout.writeLine 'consists of four integers separated by single spaces, with no additional spaces'
        sysout.writeLine 'in the string. The first two integers are the coordinates of the bottom left'
        sysout.writeLine 'corner in the given rectangle, and the last two integers are the coordinates of'
        sysout.writeLine 'the top right corner.'
        sysout.writeLine ''
        sysout.writeLine 'If a barren land definition is outside the bounds of the land space, the parts'
        sysout.writeLine 'of the definition exceeding that space is ignored.'
        sysout.writeLine ''
        sysout.writeLine '== EXAMPLES =='
        sysout.writeLine 'Example 1: A land space having no barren land:'
        sysout.writeLine 'usage: BarrenLandAnalysis'
        sysout.writeLine ''
        sysout.writeLine 'Example 2: A land space having one barren land definition:'
        sysout.writeLine 'usage: BarrenLandAnalysis "0 292 399 307"'
        sysout.writeLine ''
        sysout.writeLine 'Example 3: A land space having multiple barren land definitions:'
        sysout.writeLine 'usage: BarrenLandAnalysis "48 192 351 207", "48 392 351 407"'

        sysout.flush()
    }

    public static void main(String... args) {

        println "running with args ${args}..."

        def arableLand = new BarrenLandAnalysis().run(args)

        if (arableLand) {
            println arableLand
        }

        println '...done.'

    }
}
