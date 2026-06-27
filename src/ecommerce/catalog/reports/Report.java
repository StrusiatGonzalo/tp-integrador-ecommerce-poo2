package ecommerce.catalog.reports;

public interface Report {
    String accept(ReportVisitor visitor);
}
