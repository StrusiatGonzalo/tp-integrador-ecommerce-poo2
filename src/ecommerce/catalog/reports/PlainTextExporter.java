package ecommerce.catalog.reports;

public class PlainTextExporter implements ReportVisitor {
    @Override
    public String visit(ProductSalesReport report) {
        StringBuilder sb = new StringBuilder("REPORTE DE PRODUCTOS MAS VENDIDOS");
        for (SalesEntry e : report.getEntries()) {
            sb.append(String.format("%-30s | unidades: %-5d | precio promedio: $%.2f%n",
                e.getItem().getName(), e.getUnitsSold(), e.getAveragePricePaid()));
        }
        return sb.toString();
    }
}