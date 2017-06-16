package controller.ui.room;

import controller.ui.UIController;
import javax.swing.JPanel;
import model.IResult;
import view.room.UIPagingView;

public class UIPagingController extends UIController {
    
    private final JPanel pagingListPanel;
    
    public UIPagingController(UIPagingView uiPagingView) {
        super(uiPagingView);
        
        pagingListPanel = (JPanel)super.findViewById("PagingListPanel");
    }
    
    // Return the first cell
    public UIPagingCellController paging(int nPages, IResult.ResponseReceiver listener) {
        if (nPages < 1) {
            return null;
        }
        
        pagingListPanel.removeAll();
        
        UIPagingCellController firstCellController = new UIPagingCellController();
        firstCellController.setId(1);
        firstCellController.setTitle("1");
        firstCellController.setMouseListener(listener);
        firstCellController.check();
        pagingListPanel.add(firstCellController.getContentView());
        
        UIPagingCellController cellController;
        for (int iPage = 1; iPage < nPages; ++iPage) {
            cellController = new UIPagingCellController();
            cellController.setId(iPage + 1);
            cellController.setTitle((iPage + 1) + "");
            cellController.setMouseListener(listener);
            pagingListPanel.add(cellController.getContentView());
        }
        
        pagingListPanel.validate();
        
        return firstCellController;
    }
    
}
