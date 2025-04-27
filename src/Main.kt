fun main() {
    val notes = TextFile("src/files/notes.txt");
    val excell = CsvFile("src/files/Salaries.csv")

    println("Text File Info:")
    println(notes.getFileCreationTime());
    println(notes.getFilelastModifiedTime());
    println(notes.getFilelastAccessedTime());

    println("CSV File Info:")
    println(excell.getFileCreationTime());
    println(excell.getFilelastModifiedTime());
    println(excell.getFilelastAccessedTime());
}