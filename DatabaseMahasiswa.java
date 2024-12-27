import java.io.*;
import java.util.*;

public class DatabaseMahasiswa {
    static final String FILE_NAME = "ListMahasiswa.txt";
    static final int MAX_STUDENTS = 100;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nProgram Data Mahasiswa");
            System.out.println("1. Lihat Data Mahasiswa");
            System.out.println("2. Tambah Data Mahasiswa");
            System.out.println("3. Perbarui Data Mahasiswa");
            System.out.println("4. Hapus Data Mahasiswa");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            String choice = s.nextLine();

            switch (choice) {
                case "1" -> viewStudents();
                case "2" -> addStudent(s);
                case "3" -> updateStudent(s);
                case "4" -> deleteStudent(s);
                case "5" -> exit = true;
                default  -> System.out.println("\nPilihan invalid. Silakan coba lagi.");
            }
        }
        s.close();
    }

    static void viewStudents() {
        List<String> students = readStudents();
        if (students.isEmpty()) {
            System.out.println("\nTidak ada data mahasiswa");
        } else {
            System.out.println("\nDATA MAHASISWA");
            for (String student : students) {
                System.out.println(student);
            }
        }
    }

    static void addStudent(Scanner scanner) {
        List<String> students = readStudents();
        if (students.size() >= MAX_STUDENTS) {
            System.out.println("\nError: Kapasitas Sudah Penuh");
            return;
        }

        System.out.print("\nNIM: ");
        String nim = scanner.nextLine();
        if (nimExists(nim, students)) {
            System.out.println("Error: NIM Sudah Terdaftar");
            return;
        }

        System.out.print("Nama: ");
        String name = scanner.nextLine();
        students.add(nim + " " + name);
        writeStudents(students);
        System.out.println("Sukses Menambahkan Data");
    }

    static void updateStudent(Scanner scanner) {
        List<String> students = readStudents();
        System.out.print("\nNIM: ");
        String nim = scanner.nextLine();

        int index = findStudentIndex(nim, students);
        if (index == -1) {
            System.out.println("Error: NIM Tidak Ditemukan");
            return;
        }

        System.out.print("Nama Baru: ");
        String name = scanner.nextLine();
        students.set(index, nim + " " + name);
        writeStudents(students);
        System.out.println("Sukses Memperbarui Data");
    }

    static void deleteStudent(Scanner scanner) {
        List<String> students = readStudents();
        System.out.print("\nNIM: ");
        String nim = scanner.nextLine();

        int index = findStudentIndex(nim, students);
        if (index == -1) {
            System.out.println("Error: NIM Tidak Ditemukan");
            return;
        }

        students.remove(index);
        writeStudents(students);
        System.out.println("Sukses Menghapus Data");
    }

    static List<String> readStudents() {
        List<String> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(line);
            }
        } catch (IOException e) {
            System.out.println("\nKesalahan membaca file: " + e.getMessage());
        }
        return students;
    }

    static void writeStudents(List<String> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String student : students) {
                writer.write(student);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("\nKesalahan menulis file: " + e.getMessage());
        }
    }

    static int findStudentIndex(String nim, List<String> students) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).startsWith(nim + " ")) {
                return i;
            }
        }
        return -1;
    }

    static boolean nimExists(String nim, List<String> students) {
        return students.stream().anyMatch(student -> student.startsWith(nim + " "));
    }
}