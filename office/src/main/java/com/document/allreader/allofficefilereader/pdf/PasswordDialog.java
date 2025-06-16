
package com.document.allreader.allofficefilereader.pdf;

import android.content.DialogInterface;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.document.allreader.allofficefilereader.common.ICustomDialog;
import com.document.allreader.allofficefilereader.constant.EventConstant;
import com.document.allreader.allofficefilereader.fc.pdf.PDFLib;
import com.document.allreader.allofficefilereader.res.ResKit;
import com.document.allreader.allofficefilereader.system.IControl;


public class PasswordDialog
{
    

    public PasswordDialog(IControl control, PDFLib lib)
    {
        this.control = control;
        this.lib = lib;
    }
    

    public void show()
    {
        if(control.getMainFrame().isShowPasswordDlg())
        {
            alertBuilder = new AlertDialog.Builder(control.getActivity());
            requestPassword(lib); 
        }
        else
        {
            ICustomDialog dlgListener = control.getCustomDialog();
            if(dlgListener != null)
            {
                dlgListener.showDialog(ICustomDialog.DIALOGTYPE_PASSWORD);
            }
        }
    }

    /**
     * 
     * @param savedInstanceState
     */
    private void requestPassword(final PDFLib lib)
    {
        pwView = new EditText(control.getActivity());
        pwView.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        pwView.setTransformationMethod(new PasswordTransformationMethod());

        AlertDialog alert = alertBuilder.create();
        alert.setTitle(ResKit.instance().getLocalString("DIALOG_ENTER_PASSWORD"));
        alert.setView(pwView);
        alert.setButton(AlertDialog.BUTTON_POSITIVE, 
            ResKit.instance().getLocalString("BUTTON_OK"), 
            new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                if (lib.authenticatePasswordSync(pwView.getText().toString()))
                {
                    control.actionEvent(EventConstant.APP_PASSWORD_OK_INIT, null);
                    return;
                }
                else
                {
                    requestPassword(lib);
                }
            }
        });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE,
            ResKit.instance().getLocalString("BUTTON_CANCEL"),
            new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    //lib.dispose();
                    control.getActivity().onBackPressed();
                    //control.getMainFrame().destroyEngine();
                }
            });
        
        alert.setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    dialog.dismiss();
                    control.getActivity().onBackPressed();
                    //control.getMainFrame().destroyEngine();
                    return true;
                }
                return false;
            }
        });
        alert.show();
    }
    
    //
    private IControl control;
    //
    private PDFLib lib;
    //
    private AlertDialog.Builder alertBuilder;
    //
    private EditText pwView;
}
