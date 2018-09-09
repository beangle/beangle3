[#ftl]
<table width="100%" class="pagebar">
  <tr class="tfoot">
    <td colspan="13" align="center">
      <img src="${base}/static/images/action/firstcurPage.gif"title="第一页" onclick="goPage('${pageId}',1);"/>
      <img src="${base}/static/images/action/prevcurPage.gif" title="前一页" onclick="goPage('${pageId}',${(curPage.previousPageIndex)!(previousPageIndex)});"/>
      <img src="${base}/static/images/action/nextcurPage.gif" title="下一页" onclick="goPage('${pageId}',${(curPage.nextPageIndex)!(nextPageIndex)})"/>
      <img src="${base}/static/images/action/lastcurPage.gif" title="最后页" onclick="goPage('${pageId}',${(curPage.totalPages)!(totalPages)})"/>
      ${(curPage.pageIndex)}/${(curPage.totalPages)}
    </td>
  </tr>
</table>
