package ecommerce.catalog.reports;

public interface ReportVisitor {
    String visit(ProductSalesReport report);
}
