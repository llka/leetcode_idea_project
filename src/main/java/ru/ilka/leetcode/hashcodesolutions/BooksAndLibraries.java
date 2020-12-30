package ru.ilka.leetcode.hashcodesolutions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class BooksAndLibraries {
    private static final Logger logger = LogManager.getLogger(BooksAndLibraries.class);

    List<Library> libraries;
    Long librariesCount;
    Long booksCount;
    Long daysAvailable;
    Set<Book> booksAvailable;
    HashMap<Long, Book> booksAvailableHashMapById;

    HashSet<Book> ourBooks;
    List<IterationResult> usedLibrariesWithBooks;

    public BooksAndLibraries() {
        libraries = new ArrayList<>();
        booksAvailable = new HashSet<>();
        booksAvailableHashMapById = new HashMap<>();
        ourBooks = new HashSet<>();
        usedLibrariesWithBooks = new ArrayList<>();
    }

    public void solution(String inputFile, String outputName) throws IOException {
        readFile(inputFile);
//
//        logger.info("Libraries: ");
//        logger.info(libraries);
//        logger.debug("----");
//        logger.info("Books: ");
//        logger.info(booksAvailable);

        solve();

        writeResultIntoFile(outputName);
    }

    private void solve() {
        int iteration = 0;
        IterationResult iterationResult = chooseNextLib();
        while (iterationResult.library != null) {
            logger.debug("iteration result:");
            logger.info(iterationResult);
            iterationResult.library.order = iteration;
            daysAvailable -= iterationResult.library.daysToSignUp;
//               logger.debug("daysAvailable: {}", daysAvailable);
            ourBooks.addAll(iterationResult.books);
            usedLibrariesWithBooks.add(iterationResult);
            iterationResult = chooseNextLib();
        }
    }

    private IterationResult chooseNextLib() {
        // logger.debug(" -----  iteration -----");
        double result = 0;
        Library resultLib = null;
        Set<Book> appropriateBooks = new HashSet<>();
        for (Library lib : libraries) {
            Set<Book> appropriateBooksTemp = new HashSet<>();
            if (lib.order == -1 && daysAvailable - lib.daysToSignUp > 0) {
                long maxBooksIm = (daysAvailable - lib.daysToSignUp) * lib.booksPerDay;
                double booksSum = 0;

                for (Book b : lib.booksSortedByCost) {
                    if (maxBooksIm == 0) {
                        break;
                    }
                    if (!ourBooks.contains(b)) {
                        booksSum += b.score;
                        maxBooksIm--;
                        appropriateBooksTemp.add(b);
                    }
                }

                booksSum = booksSum * (((double) (daysAvailable +  5.3213 * lib.daysToSignUp)) / lib.daysToSignUp);

                if (booksSum > result) {
                    result = booksSum;
                    resultLib = lib;
                    appropriateBooks.clear();
                    appropriateBooks.addAll(appropriateBooksTemp);
                }
            }
        }
        // logger.debug(" -----  iteration end -----");
        return new IterationResult(appropriateBooks, resultLib);
    }

    private void writeResultIntoFile(String outputFile) {
        logger.warn("result sum: {}", usedLibrariesWithBooks.stream()
                .mapToLong(i -> i.books.stream().mapToLong(Book::getScore).sum())
                .sum());

        try (FileWriter writer = new FileWriter(outputFile)) {

            //lib count
            writer.write(usedLibrariesWithBooks.size() + "\n");

            for (IterationResult iteration : usedLibrariesWithBooks) {
                // libId booksCount
                writer.write(iteration.library.id + " " + iteration.books.size() + "\n");
                writer.write(iteration.books.stream()
                        .map(b -> String.valueOf(b.id))
                        .collect(Collectors.joining(" ")));
                writer.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing results failed!");
        }
    }


    private void readFile(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        Scanner scanner = new Scanner(fis);

        String[] firstLine = scanner.nextLine().trim().split(" ");
        booksCount = Long.parseLong(firstLine[0]);
        booksAvailable = new HashSet<>(booksCount.intValue());
        booksAvailableHashMapById = new HashMap<>(booksCount.intValue());
        librariesCount = Long.parseLong(firstLine[1]);
        libraries = new ArrayList<>(librariesCount.intValue());
        daysAvailable = Long.parseLong(firstLine[2]);


        int bookId = 0;
        String[] secondLine = scanner.nextLine().trim().split(" ");
        for (int i = 0; i < secondLine.length; i++) {
            Book book = new Book(bookId++, Long.parseLong(secondLine[i]));
            booksAvailable.add(book);
            booksAvailableHashMapById.put(book.getId(), book);
        }


        long libraryId = 0;
        while (libraryId < librariesCount) {
            List<Long> line = Arrays.stream(scanner.nextLine().trim().split(" "))
                    .filter(s -> !s.isEmpty() && !s.isEmpty())
                    .map(Long::parseLong).collect(Collectors.toList());
//            logger.info("libId : {}", libraryId);
//            logger.debug("lib meta: {}", line);
            Library library = new Library(libraryId++,
                    Arrays.stream(scanner.nextLine().trim().split(" "))
                            .map(Long::parseLong)
                            .map(id -> booksAvailableHashMapById.get(id))
                            .sorted(Comparator.comparingLong(Book::getScore).reversed())
                            .collect(Collectors.toList()),
                    line.get(1),
                    line.get(2));
            libraries.add(library);
        }
        scanner.close();
    }

    class IterationResult {
        Set<Book> books;
        Library library;

        public IterationResult(Set<Book> books, Library library) {
            this.books = books;
            this.library = library;
        }

        @Override
        public String toString() {
            return "IterationResult{" +
                    "books=" + books +
                    ", library=" + library +
                    '}';
        }
    }

    class Book {
        long id;
        long score;

        public Book(long id, long score) {
            this.id = id;
            this.score = score;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getScore() {
            return score;
        }

        public void setScore(long score) {
            this.score = score;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return id == book.id &&
                    score == book.score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, score);
        }

        @Override
        public String toString() {
            return "Book{" +
                    "id=" + id +
                    ", score=" + score +
                    '}';
        }
    }

    class Library {
        long id;
        List<Book> booksSortedByCost;
        long daysToSignUp;
        long booksPerDay;
        int order = -1;

        public Library(long id, List<Book> booksSortedByCost, long daysToSignUp, long booksPerDay) {
            this.id = id;
            this.booksSortedByCost = booksSortedByCost;
            this.daysToSignUp = daysToSignUp;
            this.booksPerDay = booksPerDay;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Library library = (Library) o;
            return id == library.id &&
                    daysToSignUp == library.daysToSignUp &&
                    booksPerDay == library.booksPerDay &&
                    Objects.equals(booksSortedByCost, library.booksSortedByCost);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, booksSortedByCost, daysToSignUp, booksPerDay);
        }

        @Override
        public String toString() {
            return "Library{" +
                    "id=" + id +
                    ", booksSortedByCost=" + booksSortedByCost +
                    ", daysToSignUp=" + daysToSignUp +
                    ", booksPerDay=" + booksPerDay +
                    ", order=" + order +
                    '}';
        }
    }

}
