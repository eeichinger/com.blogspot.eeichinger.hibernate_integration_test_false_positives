package hibernate_integration_test_false_positives;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public abstract class Address {
    private String town;
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Address that = (Address) o;

        return new EqualsBuilder()
                .append(this.town, that.town)
                .append(this.country, that.country)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(31, 19)
                .append(this.town)
                .append(this.country)
                .toHashCode();
    }
    
    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
