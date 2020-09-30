import java.text.DecimalFormat

data class Matrix(val column: List<Row>) {

    fun add(matrix: Matrix): Matrix? {
        return if (this.column.size == matrix.column.size && this.column[0].row.size == matrix.column[0].row.size) {
            val hold = mutableListOf<Row>()
            for (i in this.column.indices) hold.add(this.column[i].add(matrix.column[i]))
            Matrix(hold)
        } else null
    }

    fun times(constant: Double): Matrix {
        val hold = mutableListOf<Row>()
        for (i in this.column) hold.add(i.times(constant))
        return Matrix(hold)
    }

    fun times(matrix: Matrix): Matrix? {
        return if (this.column.size == matrix.column[0].row.size) {
            val holdMatrix = mutableListOf<Row>()
            for (i in matrix.column) {
                val holdRow = mutableListOf<Double>()
                for (j in this.column[0].row.indices) holdRow.add(i.times(this.columnToRow(j)))
                holdMatrix.add(Row(holdRow))
            }
            Matrix(holdMatrix)
        } else null
    }

    private fun columnToRow(index: Int): Row {
        val hold = mutableListOf<Double>()
        for (i in this.column.indices) hold.add(this.column[i].row[index])
        return Row(hold)
    }

    fun transposeMain() = transpose(1)

    fun transposeSide() = transpose(2)

    fun transposeVertical() = transpose(3)

    fun transposeHorizontal() = transpose(4)

    private fun transpose(type: Int): Matrix {
        val holdMatrix = mutableListOf<Row>()
        when (type) {
            1, 3 -> for (i in this.column.indices)
                holdMatrix.add(if (type == 3) this.column[i].reversed() else columnToRow(i))
            else -> for (i in this.column.lastIndex downTo 0)
                holdMatrix.add(if (type == 2) columnToRow(i).reversed() else this.column[i])
        }
        return Matrix(holdMatrix)
    }

    fun determinant(): Double? {
        return if (this.column.size == this.column[0].row.size) determinantWork(Matrix(this.column)) else null
    }

    private fun determinantWork(matrix: Matrix): Double {
        var d = 0.0
        when {
            matrix.column.size > 2 -> {
                var positive = true
                for (i in matrix.column[0].row.indices) {
                    val number = { matrix.column[0].row[i] }
                    val work = { determinantWork(filteredMatrix(matrix, 0, i)) }
                    val getValue = { if (number() == 0.0) 0.0 else number() * work() }
                    if (positive) {
                        d += getValue()
                    } else d -= getValue()
                    positive = !positive
                }
            }
            matrix.column.size == 2 -> d =
                matrix.column[0].row[0] * matrix.column[1].row[1] - matrix.column[1].row[0] * matrix.column[0].row[1]
            else -> d = matrix.column[0].row[0]
        }
        return d
    }

    private fun filteredMatrix(matrix: Matrix, column: Int, index: Int): Matrix {
        val holdMatrix = mutableListOf<Row>()
        for (i in matrix.column.indices) {
            if (i != column) {
                val holdRow = mutableListOf<Double>()
                for (j in matrix.column[i].row.indices) if (j != index) holdRow.add(matrix.column[i].row[j])
                holdMatrix.add(Row(holdRow))
            }
        }
        return Matrix(holdMatrix)
    }

    fun inverse(): Matrix? {
        val d = determinant()
        return if (d != 0.0 && d != null) {
            val holdMatrix = mutableListOf<Row>()
            when {
                this.column.size > 2 -> {
                    for (i in this.column.indices) {
                        var positive = i % 2 == 0
                        val holdRow = mutableListOf<Double>()
                        for (j in this.column[i].row.indices) {
                            val work = { (1 / d) * determinantWork(filteredMatrix(Matrix(this.column), i, j)) }
                            if (positive) holdRow.add(work()) else {
                                holdRow.add(-work())
                            }
                            positive = !positive
                        }
                        holdMatrix.add(Row(holdRow))
                    }
                    Matrix(holdMatrix).transposeMain()
                }
                column.size == 2 -> {
                    holdMatrix.add(Row(listOf(column[1].row[1], -column[0].row[1])))
                    holdMatrix.add(Row(listOf(-column[1].row[0], column[0].row[0])))
                    Matrix(holdMatrix).times(1 / d)
                }
                else -> Matrix(listOf(Row(listOf(1.0))))
            }
        } else null
    }

    fun print() {
        val matrixStr = Array(this.column.size) { Array(this.column[0].row.size) { "" } }
        val max = Array(this.column[0].row.size) { 0 }
        val format = DecimalFormat("#.##")
        format.isDecimalSeparatorAlwaysShown = false
        for (i in this.column.indices) {
            for (j in this.column[i].row.indices) {
                val getNum = { this.column[i].row[j] }
                val num = if (getNum() == -0.0) 0.0 else getNum()
                matrixStr[i][j] += format.format(num)
                if (matrixStr[i][j].length > max[j]) max[j] = matrixStr[i][j].length
            }
        }
        for (i in matrixStr.indices) {
            for (j in matrixStr[i].indices) {
                val length = matrixStr[i][j].length
                if (length < max[j]) matrixStr[i][j] = " ".repeat(max[j] - length) + matrixStr[i][j]
            }
        }
        for (i in matrixStr.indices) {
            for (j in matrixStr[i].indices) print(matrixStr[i][j] + if (j != matrixStr[i].lastIndex) " " else "")
            println()
        }
    }
}