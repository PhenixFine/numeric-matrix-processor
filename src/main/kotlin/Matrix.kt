data class Matrix(val column: List<Row>) {

    fun add(matrix: Matrix): Matrix? {
        return if (this.column.size == matrix.column.size && this.column[0].row.size == matrix.column[0].row.size) {
            val hold = mutableListOf<Row>()
            for (i in this.column.indices) {
                hold.add(this.column[i].add(matrix.column[i]))
            }
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
                for (j in this.column[0].row.indices) {
                    holdRow.add(i.times(this.columnToRow(j)))
                }
                holdMatrix.add(Row(holdRow))
            }
            return Matrix(holdMatrix)
        } else null
    }

    private fun columnToRow(index: Int): Row {
        val hold = mutableListOf<Double>()
        for (i in this.column.indices) hold.add(this.column[i].row[index])
        return Row(hold)
    }

    fun print() {
        for (i in this.column) i.print()
    }
}