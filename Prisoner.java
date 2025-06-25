public class Prisoner {
    // File name constant
    public static final String DATA_FILE = "prisonerData.tsv";
    
    private String prisonerId;
    private String name;
    private int age;
    private int yearAdmitted;
    private int sentenceYears;
    private String reason;
    
    // Default constructor
    public Prisoner() {
    }
    
    // Parameterized constructor
    public Prisoner(String prisonerId, String name, int age, int yearAdmitted, int sentenceYears, String reason) {
        this.prisonerId = prisonerId;
        this.name = name;
        this.age = age;
        this.yearAdmitted = yearAdmitted;
        this.sentenceYears = sentenceYears;
        this.reason = reason;
    }
    
    // Constructor from TSV line
    public static Prisoner fromTSVString(String tsvLine) {
        if (tsvLine == null || tsvLine.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = tsvLine.split("\t");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid TSV format. Expected 6 fields.");
        }
        
        try {
            String prisonerId = parts[0].trim();
            String name = parts[1].trim();
            int age = Integer.parseInt(parts[2].trim());
            int yearAdmitted = Integer.parseInt(parts[3].trim());
            int sentenceYears = Integer.parseInt(parts[4].trim());
            String reason = parts[5].trim();
            
            return new Prisoner(prisonerId, name, age, yearAdmitted, sentenceYears, reason);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in TSV line: " + tsvLine);
        }
    }
    
    // Convert to TSV format
    public String toTSVString() {
        return prisonerId + "\t" + name + "\t" + age + "\t" + yearAdmitted + "\t" + sentenceYears + "\t" + reason;
    }
    
    // Get TSV header for file
    public static String getTSVHeader() {
        return "PrisonerID\tName\tAge\tYearAdmitted\tSentenceYears\tReason";
    }
    
    // Getters
    public String getPrisonerId() {
        return prisonerId;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public int getYearAdmitted() {
        return yearAdmitted;
    }
    
    public int getSentenceYears() {
        return sentenceYears;
    }
    
    public String getReason() {
        return reason;
    }
    
    // Setters with validation
    public void setPrisonerId(String prisonerId) {
        if (prisonerId == null || prisonerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Prisoner ID cannot be null or empty");
        }
        this.prisonerId = prisonerId.trim();
    }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }
    
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150");
        }
        this.age = age;
    }
    
    public void setYearAdmitted(int yearAdmitted) {
        if (yearAdmitted < 1900 || yearAdmitted > 2025) {
            throw new IllegalArgumentException("Year admitted must be between 1900 and 2025");
        }
        this.yearAdmitted = yearAdmitted;
    }
    
    public void setSentenceYears(int sentenceYears) {
        if (sentenceYears < 0 || sentenceYears > 100) {
            throw new IllegalArgumentException("Sentence years must be between 0 and 100");
        }
        this.sentenceYears = sentenceYears;
    }
    
    public void setReason(String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be null or empty");
        }
        this.reason = reason.trim();
    }
    
    // Utility methods without Math module
    public int getExpectedReleaseYear() {
        return yearAdmitted + sentenceYears;
    }
    
    public boolean isReleased() {
        return getExpectedReleaseYear() <= 2025; // Current year
    }
    
    public int getYearsServed() {
        int served = 2025 - yearAdmitted;
        if (served > sentenceYears) {
            return sentenceYears;
        }
        return served;
    }
    
    public int getRemainingYears() {
        int remaining = sentenceYears - getYearsServed();
        if (remaining < 0) {
            return 0;
        }
        return remaining;
    }
    
    public String getStatus() {
        if (isReleased()) {
            return "Released";
        } else {
            return "Serving (" + getRemainingYears() + " years remaining)";
        }
    }
    
    // Validation method
    public boolean isValid() {
        try {
            return prisonerId != null && !prisonerId.trim().isEmpty() &&
                   name != null && !name.trim().isEmpty() &&
                   age >= 0 && age <= 150 &&
                   yearAdmitted >= 1900 && yearAdmitted <= 2025 &&
                   sentenceYears >= 0 && sentenceYears <= 100 &&
                   reason != null && !reason.trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    // Search helper methods
    public boolean matchesId(String searchId) {
        return prisonerId != null && prisonerId.equalsIgnoreCase(searchId.trim());
    }
    
    public boolean matchesName(String searchName) {
        return name != null && name.toLowerCase().contains(searchName.toLowerCase().trim());
    }
    
    public boolean matchesReason(String searchReason) {
        return reason != null && reason.toLowerCase().contains(searchReason.toLowerCase().trim());
    }
    
    public boolean matchesAnyField(String searchTerm) {
        String term = searchTerm.toLowerCase().trim();
        return matchesName(term) || 
               matchesReason(term) || 
               matchesId(term) ||
               String.valueOf(age).contains(term) ||
               String.valueOf(yearAdmitted).contains(term) ||
               String.valueOf(sentenceYears).contains(term);
    }
    
    // Data formatting methods for display
    public String getFormattedInfo() {
        return String.format("ID: %s | Name: %s | Age: %d | Admitted: %d | Sentence: %d years | Status: %s",
                           prisonerId, name, age, yearAdmitted, sentenceYears, getStatus());
    }
    
    public String getShortInfo() {
        return String.format("%s - %s (%s)", prisonerId, name, getStatus());
    }
    
    // Method to sanitize data for TSV (handle tabs and newlines)
    private String sanitizeForTSV(String input) {
        if (input == null) return "";
        return input.replace("\t", " ").replace("\n", " ").replace("\r", " ").trim();
    }
    
    // Enhanced TSV conversion with sanitization
    public String toSafeTSVString() {
        return sanitizeForTSV(prisonerId) + "\t" + 
               sanitizeForTSV(name) + "\t" + 
               age + "\t" + 
               yearAdmitted + "\t" + 
               sentenceYears + "\t" + 
               sanitizeForTSV(reason);
    }
    
    // Override toString for display purposes
    @Override
    public String toString() {
        return getFormattedInfo();
    }
    
    // Create a copy of the prisoner
    public Prisoner copy() {
        return new Prisoner(prisonerId, name, age, yearAdmitted, sentenceYears, reason);
    }
    
    // Static method to validate prisoner ID format
    public static boolean isValidIdFormat(String id) {
        return id != null && id.matches("P\\d{3,}"); // Format: P001, P002, etc.
    }
    
    // Generate next available ID (to be used with existing prisoner list)
    public static String generateNextId(java.util.List<Prisoner> existingPrisoners) {
        int maxId = 0;
        for (Prisoner p : existingPrisoners) {
            if (p.getPrisonerId().startsWith("P")) {
                try {
                    int currentId = Integer.parseInt(p.getPrisonerId().substring(1));
                    if (currentId > maxId) {
                        maxId = currentId;
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid ID formats
                }
            }
        }
        return String.format("P%03d", maxId + 1);
    }
}