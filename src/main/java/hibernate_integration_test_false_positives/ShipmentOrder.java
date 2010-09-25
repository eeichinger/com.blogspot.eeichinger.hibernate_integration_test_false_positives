package hibernate_integration_test_false_positives;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
public final class ShipmentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String customerName;
    @Embedded
    private DeliveryAddress deliveryAddress;

    public ShipmentOrder() {
        initialize();
    }

    @PostLoad
    private void initialize() {
        // workaround for bug in hibernate, setting the nested composite null if none of its fields are populated
        if (deliveryAddress == null) {
            deliveryAddress = new DeliveryAddress();
        }
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentOrder that = (ShipmentOrder) o;

        return new EqualsBuilder()
                .append(this.id, that.id)
                .append(this.customerName, that.customerName)
                .append(this.deliveryAddress, that.deliveryAddress)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 7)
                .append(this.id)
                .append(this.customerName)
                .append(this.deliveryAddress)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
