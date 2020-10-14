package life.majiang.community.dto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;//展示前一页按钮
    private boolean showFirstPage;//展示第一页按钮
    private boolean showNext;//展示下一页按钮
    private boolean showEndPage;//展示最后一页按钮
    private Integer page;//当前页面数
    private List<Integer> pages = new ArrayList<>();//页面列
    private Integer totalPage;
    // 分页方法
    public void setPagination(Integer totalPage, Integer page) {

        this.totalPage = totalPage;
        this.page = page;

        //显示前123的逻辑和显示最后三个的逻辑
        pages.add(page);
        for (int i =1 ; i<=3 ; i++){
            if (page - i > 0){
                pages.add(0,page - i );//往头部插入
            }
            if (page + i <= totalPage){
                pages.add(page + i );//往尾部插入
            }
        }
        //是否显示上一页
        if (page == 1 ){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否显示下一页
        if (page == totalPage ){
            showNext = false;
        }else {
            showNext = true;
        }
        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }

    }
}
