fun main() {
    val choice = { getString(menu()).toIntOrNull() }
    var command: Int? = null

    while (command != 0) {
        command = choice()
        when (command) {
            1, 3 -> matricesWork(command)
            2 -> matrixTimesConstant()
            4 -> transpose()
            5 -> determinant()
        }
        println()
    }
}

private fun menu(): String {
    return ("1. Add matrices\n" +
            "2. Multiply matrix by a constant\n" +
            "3. Multiply matrices\n" +
            "4. Transpose matrix\n" +
            "5. Calculate a determinant\n" +
            "0. Exit\n" +
            "Your choice: ")
}

private fun matricesWork(command: Int) {
    val matrix1 = getMatrix(" first")
    val matrix2 = getMatrix(" second")

    println("The result is:")
    when (command) {
        1 -> matrix2.add(matrix1)?.print() ?: error()
        3 -> matrix2.times(matrix1)?.print() ?: error()
    }
}

private fun matrixTimesConstant() {
    val matrix = getMatrix()
    val number = getNum("Enter constant: ")

    println("The result is:")
    matrix.times(number).print()
}

private fun transpose() {
    val choice = getString(transposeMenu()).toIntOrNull()
    if (!(1..4).contains(choice)) return
    val matrix: Matrix = getMatrix()

    println("The result is:")
    when (choice) {
        1 -> matrix.transposeMain().print()
        2 -> matrix.transposeSide().print()
        3 -> matrix.transposeVertical().print()
        4 -> matrix.transposeHorizontal().print()
    }
}

private fun transposeMenu(): String {
    return ("\n1. Main diagonal\n" +
            "2. Side diagonal\n" +
            "3. Vertical line\n" +
            "4. Horizontal line\n" +
            "Your choice: ")
}

private fun determinant() {
    val number = getMatrix().determinant()

    println("The result is:")
    if (number != null) println(number) else error()
}

private fun getMatrix(print: String = ""): Matrix {
    var sizes = getList("Enter size of$print matrix: ")
    while (sizes.size != 2 || sizes[0] < 1 || sizes[1] < 1) sizes =
        getList("Please enter two whole numbers greater than zero for the Matrix size: ")

    println("Enter$print matrix:")
    return Matrix(List(sizes[0].toInt()) { getRow(sizes[1].toInt()) })
}

private fun getRow(size: Int): Row {
    var hold = Row(getList())
    while (hold.row.size != size) hold =
        Row(getList("You did not enter $size numbers for the row. Please try again:\n"))

    return hold
}

private fun getList(text: String = ""): List<Double> {
    return try {
        getString(text).trim().split(' ').map { it.toDouble() }
    } catch (e: Exception) {
        getList("Please enter numbers: ")
    }
}

private fun error() = println("The operation cannot be performed.")

private fun getNum(text: String, defaultMessage: Boolean = false): Double {
    val strErrorNum = " was not a number, please try again: "
    var num = text
    var default = defaultMessage

    do {
        num = getString(if (default) num + strErrorNum else num)
        if (!default) default = true
    } while (!isNumber(num))

    return num.toDouble()
}

private fun getString(text: String): String {
    print(text)
    return readLine()!!
}

private fun isNumber(number: String) = number.toDoubleOrNull() != null