package ecommerce.catalog.reports;

import java.util.List;

public class ProductSalesReport implements Report {
	private final List<SalesEntry> entries;

    public ProductSalesReport(List<SalesEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String accept(ReportVisitor visitor) {
        return visitor.visit(this);
    }

    public List<SalesEntry> getEntries() {
        return entries;
    }
}
