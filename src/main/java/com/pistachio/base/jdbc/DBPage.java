
package com.pistachio.base.jdbc;

import java.util.List;

/**
 * @desc data page , convenient for data access
 */
public class DBPage
{
    //default data rows in 1 page
    public static final int NUMBERS_PER_PAGE = 10;
    //the number of data per page
    private int numPerPage;
    //recored amount
    private int totalRows;
    //page amount
    private int totalPages;
    //current page
    private int currentPage;
    //start line
    private int startIndex;
    //last llin
    private int lastIndex;
    //the record set saved to List
    private List dataList;

    private boolean firstFlag = true;
    private boolean prevFlag = true;
    private boolean nextFlag = true;
    private boolean lastFlag = true;
    private String buttonType = "";

    /**
     * the constructor of page
     *
     * @param currentPage
     * @param numPerPage
     */
    public DBPage(int currentPage, int numPerPage)
    {
        //set the number of every page
        this.numPerPage = numPerPage;
        //set the number of pages
        this.currentPage = currentPage;
    }

    /**
     * set the total records, this function will calculate other number after set this value
     *
     * @param totalRows
     * @param totalRows
     */
    public void setTotalRows(int totalRows)
    {
        //set total rows
        this.totalRows = totalRows;
        //calculate the total pages
        calcTotalPages();
        //calculate start line
        calcStartIndex();
        //calculate last line
        calcLastIndex();
        //calcute late page button information
        setPageButton();
    }

    /**
     * set page button information
     */
    private void setPageButton()
    {
        if (totalPages == 0 || totalPages == 1)
        {
            firstFlag = false;
            prevFlag = false;
            nextFlag = false;
            lastFlag = false;
            return;
        }

        if (currentPage > totalPages)
        {
            currentPage = totalPages;
        }

        if (currentPage <= 1 || currentPage == 0)
        {
            firstFlag = false;
            prevFlag = false;
        }
        if (currentPage >= totalPages || totalPages == 0)
        {
            lastFlag = false;
            nextFlag = false;
        }
    }


    private void calcTotalPages()
    {
        if (totalRows % numPerPage == 0)
        {
            this.totalPages = totalRows / numPerPage;
        }
        else
        {
            this.totalPages = (totalRows / numPerPage) + 1;
        }
    }


    private void calcStartIndex()
    {
        this.startIndex = (currentPage - 1) * numPerPage;
    }

    private void calcLastIndex()
    {
        if (totalRows < numPerPage)
        {
            this.lastIndex = totalRows;
        }
        else if ((totalRows % numPerPage == 0) || (totalRows % numPerPage != 0 && currentPage < totalPages))
        {
            this.lastIndex = currentPage * numPerPage;
        }
        else if (totalRows % numPerPage != 0 && currentPage == totalPages)
        {
            this.lastIndex = totalRows;
        }
    }

    private String renderButton(String show, boolean disabled, int page)
    {
        if (buttonType.equalsIgnoreCase("text"))
            return renderText(show, disabled, page);

        String temp = "";
        if (disabled == true)
            temp = "disabled";
        return "<input type=\"submit\" class=\"pageButton\" value=\"" + show + "\" onclick=\"goToPage(" + page + ",this.form)\" " + temp + ">\n";
    }

    private String renderText(String show, boolean disabled, int page)
    {
        if (disabled == true)
            return show;
        return "<a class=\"pageLink\" href=\"javascript:toPage(" + page + ")\">" + show + "</a>";
    }

    public String first(String show)
    {
        if (firstFlag == false)
            return renderButton(show, true, 0);
        return renderButton(show, false, 1);
    }

    public String preview(String show)
    {
        if (prevFlag == false)
            return renderButton(show, true, 0);
        return renderButton(show, false, (currentPage - 1));
    }

    public String next(String show)
    {
        if (nextFlag == false)
            return renderButton(show, true, 0);
        return renderButton(show, false, (currentPage + 1));
    }

    public String last(String show)
    {
        if (lastFlag == false)
            return renderButton(show, true, 0);
        return renderButton(show, false, totalPages);
    }


    public void setButtonType(String buttonType)
    {
        this.buttonType = buttonType;
    }

    /**
     * get current page number
     *
     * @return
     */
    public int getCurrentPage()
    {
        return currentPage;
    }

    /**
     * get the number of each page
     *
     * @return
     */
    public int getNumPerPage()
    {
        return numPerPage;
    }

    /**
     * get the data
     *
     * @return
     */
    public List getData()
    {
        if ( dataList!=null&&dataList.size()>getNumPerPage() )
        {
            return dataList.subList(getStartIndex(), getLastIndex());
        }
        return dataList;
    }

    /**
     * get total pages
     *
     * @return
     */
    public int getTotalPages()
    {
        return totalPages;
    }

    /**
     * get total rows
     *
     * @return
     */
    public int getTotalRows()
    {
        return totalRows;
    }

    /**
     * get the start index of paging
     *
     * @return
     */
    public int getStartIndex()
    {
        return startIndex;
    }

    /**
     * get the last index of paging
     *
     * @return
     */
    public int getLastIndex()
    {
        return lastIndex;
    }

    /**
     * set the data of page object
     *
     * @param dataList
     */
    public void setData(List dataList)
    {
        this.dataList = dataList;
    }
    
    /**
     * construct page string according to url
     * @param url url
     * @return page string
     */
    public String getLinkStr(String url,String path)
    {
        String linkStr = "";
        String scriptTmp = "";

        int pageNumber = this.currentPage;
        int pages = this.totalPages;
        int total = this.totalRows;

        linkStr += "  <b>" + total + "</b> rows&nbsp;current <b>" + pageNumber +
            "</b>/<b>" + pages + "</b> pages&nbsp;&nbsp;";
        if (url.indexOf("?") > 0)
        { //already taken a parameter
            url = url + "&";
        }
        else
        {
            url = url + "?";
        }
        if ( (pageNumber == 1) && (pageNumber < pages))
        {
            linkStr += "First&nbsp;Previous&nbsp;<a href='" + url + "&pageNumber=" +
                (pageNumber + 1) +
                "'>Next</a>&nbsp;<a href='" + url +
                "&pageNumber=" +
                pages + "'>Last</a>&nbsp;Jump<input type=text name=jump id=jump size=3 style=\"text-align:center\" value='"+pageNumber+"'>page&nbsp;<img border=\"0\" src=\""+path+"/images/button020.gif\" width=\"20\" height=\"18\"  style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        }
        else if ( (pageNumber > 1) && (pageNumber < pages))
        {

            linkStr += "<a href='" + url +
                "&pageNumber=1'>First</a>&nbsp;<a href='" +
                url + "&pageNumber=" + (pageNumber - 1) +
                "'>Previous</a>&nbsp;<a href='" + url +
                "&pageNumber=" +
                (pageNumber + 1) + "'>Next&nbsp;<a href='" +
                url +
                "&pageNumber=" +
                pages + "'>Last</a>&nbsp;Jump<input type=text name=jump id=jump size=3 style=\"text-align:center\" value='"+pageNumber+"'>page&nbsp;<img border=\"0\" src=\""+path+"/images/button020.gif\" width=\"20\" height=\"18\"  style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        }
        else if ( (pageNumber == pages) && (pages > 1))
        {
            linkStr += "<a href='" + url +
                "&pageNumber=1'>First</a>&nbsp;<a href='" +
                url + "&pageNumber=" + (pageNumber - 1) +
                "'>Previous</a>&nbsp;Next&nbsp;Last&nbsp;Jump<input type=text name=jump id=jump size=3 style=\"text-align:center\" value='"+pageNumber+"'>page&nbsp;<img border=\"0\" src=\""+path+"/images/button020.gif\" width=\"20\" height=\"18\" style=\"cursor:hand;\" onclick=\"javascript:checkPage();\">";
        }
        scriptTmp += "<SCRIPT LANGUAGE=\"JavaScript\">\n<!--\nfunction checkPage()\n {\n if(document.getElementById(\"jump\").value > " +
            pages + ") {\n alert('Your input is out of limit, please try again! ');\n document.getElementById('jump').focus();\n return false;\n} else if(document.getElementById('jump').value < 1) {\n alert('Your input is out of limit, please try again! ');\n document.getElementById('jump').focus();\n return false;\n} else {\n jumpTo('" +
            url + "');\n}\n}\n//-->\n</SCRIPT>";
        scriptTmp += "<SCRIPT LANGUAGE=\"JavaScript\">\n<!--\nfunction jumpTo(url)\n {\n self.location.href=\"" +
            url + "&pageNumber=\"+" +
            "document.getElementById(\"jump\").value;\n}\n//-->\n</SCRIPT>";
        linkStr += scriptTmp;
        return linkStr;
    }
}