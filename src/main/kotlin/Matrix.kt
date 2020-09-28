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
                    val work = { determinantWork(filteredMatrix(matrix, i)) }
                    val checkValue = { if (number() == 0.0) 0.0 else number() * work() }
                    if (positive) {
                        d += checkValue()
                    } else d -= checkValue()
                    positive = !positive
                }
            }
            matrix.column.size == 2 -> d =
                matrix.column[0].row[0] * matrix.column[1].row[1] - matrix.column[1].row[0] * matrix.column[0].row[1]
            else -> d = matrix.column[0].row[0]
        }
        return d
    }

    private fun filteredMatrix(matrix: Matrix, index: Int): Matrix {
        val holdMatrix = mutableListOf<Row>()
        for (i in 1..matrix.column.lastIndex) {
            val holdRow = mutableListOf<Double>()
            for (j in matrix.column[i].row.indices) if (j != index) holdRow.add(matrix.column[i].row[j])
            holdMatrix.add(Row(holdRow))
        }
        return Matrix(holdMatrix)
    }

    fun print() {
        for (i in this.column) i.print()
    }
}