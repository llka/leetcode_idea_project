package parametrized;

public enum ArgsEnum {
    A("A", "AA"),
    B("B", "BB"),
    C("C", "CC");

    private final String providerId;
    private final String countryCode;

    ArgsEnum(String providerId, String countryCode) {
        this.providerId = providerId;
        this.countryCode = countryCode;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return providerId;
    }
}
