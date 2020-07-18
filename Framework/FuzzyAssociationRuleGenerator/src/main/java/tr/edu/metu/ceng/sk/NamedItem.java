
package tr.edu.metu.ceng.sk;

import org.jetbrains.annotations.NotNull;

import de.mrapp.apriori.Item;




public class NamedItem implements Item {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of the item.
     */
    private final String name;

    /**
     * Creates a new implementation of the type {@link Item}.
     *
     * @param name The name of the item as a {@link String}. The name may neither be null, nor
     *             empty
     */
    public NamedItem(@NotNull final String name) {
        
        this.name = name;
    }

    /**
     * Returns the name of the item.
     *
     * @return The name of the item as a {@link String}. The name may neither be null, nor empty
     */
    @NotNull
    public final String getName() {
        return name;
    }

    
    public final int compareTo(@NotNull final Item o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public final String toString() {
        return getName();
    }


    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NamedItem other = (NamedItem) obj;
        return name.equals(other.name);
    }

}
