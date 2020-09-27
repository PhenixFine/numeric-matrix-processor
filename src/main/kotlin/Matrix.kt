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

    fun print() {
        for (i in this.column) i.print()
    }
}