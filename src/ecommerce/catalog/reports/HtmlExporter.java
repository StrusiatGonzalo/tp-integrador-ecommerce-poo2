package ecommerce.catalog.reports;

public class HtmlExporter implements ReportVisitor {
    @Override
    public String visit(ProductSalesReport report) {
        StringBuilder sb = new StringBuilder("<table>\n<tr><th>Nombre</th><th>Unidades</th><th>Precio promedio</th></tr>\n");
        for (SalesEntry e : report.getEntries()) {
            sb.append("<tr><td>").append(e.getItem().getName())
              .append("</td><td>").append(e.getUnitsSold())
              .append("</td><td>$").append(String.format("%.2f", e.getAveragePricePaid()))
              .append("</td></tr>\n");
        }
        sb.append("</table>");
        return sb.toString();
    }
}