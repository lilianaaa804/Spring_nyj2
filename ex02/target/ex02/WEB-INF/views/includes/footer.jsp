<%--
  Created by IntelliJ IDEA.
  User: yeonjinoh
  Date: 2021/03/30
  Time: 6:17 오후
  To change this template use File | Settings | File Templates.
--%>
</div>
<!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!--jqery 추-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>

<!-- DataTables JavaScript -->
<script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/resources/dist/js/sb-admin-2.js"></script>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
        $(".sidebar-nav")
        .attr("class", "sidbar-nav navbar-collapse collapse")
        .attr("aria-expanded", 'false')
        .attr("style", "height: 1px");
    });
</script>

</body>

</html>