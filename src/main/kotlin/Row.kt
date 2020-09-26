import java.text.DecimalFormat

data class Row(val row: List<Double>) {

    fun add(row: Row): Row {
        val hold = mutableListOf<Double>()
        for (i in this.row.indices) {
            hold.add(this.row[i] + row.row[i])
        }
        return Row(hold)
    }

    fun times(constant: Double): Row {
        val hold = mutableListOf<Double>()
        for (num in this.row) hold.add(constant * num)
        return Row(hold)
    }

    fun times(column: Row): Double {
        var number = 0.0
       for (i in this.row.indices) {
             number += this.row[i] * column.row[i]
        }
        return number
    }

    fun print() {
        for (i in this.row.indices) {
            val format = DecimalFormat()
            format.isDecimalSeparatorAlwaysShown = false
            val num = this.row[i]
            print(format.format(num).replace(",", "") + if (i != row.lastIndex) " " else "")
        }
        println()
    }
}