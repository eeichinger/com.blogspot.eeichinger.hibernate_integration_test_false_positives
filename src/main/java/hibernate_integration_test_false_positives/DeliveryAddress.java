package hibernate_integration_test_false_positives;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import javax.persistence.Embeddable;

@Embeddable
public final class DeliveryAddress extends Address {
    private String deliveryNotes;

    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryAddress that = (DeliveryAddress) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(that))
                .append(this.deliveryNotes, that.deliveryNotes)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(37, 17)
                .appendSuper(super.hashCode())
                .append(this.deliveryNotes)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }    
}
