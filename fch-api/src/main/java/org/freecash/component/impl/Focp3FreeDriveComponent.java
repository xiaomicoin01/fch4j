package org.freecash.component.impl;

import lombok.extern.log4j.Log4j2;
import org.freecash.component.AbstractFreeDriveComponent;
import org.freecash.component.enm.FreeDriveDataLengthConst;
import org.freecash.component.enm.FreeDriveMetaDataLengthConst;
import org.freecash.domain.Knowledge;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author wanglint
 **/
@Component
@Log4j2
public class Focp3FreeDriveComponent extends AbstractFreeDriveComponent<Knowledge> {

    @Override
    public String getFreeDriveMetaDataString(Knowledge knowledge) {
        return org.freecash.utils.ProtocolUtil.encodeValue(Arrays.asList(
                    new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getProtocolName(), FreeDriveMetaDataLengthConst.PROTOCOL_NAME_LENGTH),
                    new org.freecash.utils.ProtocolUtil.EncodeItem(Integer.toString(knowledge.getProtocolNo()), FreeDriveMetaDataLengthConst.PROTOCOL_NO_LENGTH),
                    new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getProtocolVersion(), FreeDriveMetaDataLengthConst.PROTOCOL_VERSION_LENGTH),
                    new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getAction(), FreeDriveMetaDataLengthConst.ACTION_LENGTH),
                    new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getDataHash(), FreeDriveMetaDataLengthConst.DATAHASH_LENGTH),
                    new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getEncrypt(), FreeDriveMetaDataLengthConst.ENCRYPT_LENGTH),
                    new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getEncryptedPwd(), FreeDriveMetaDataLengthConst.ENCRYPTEDPWD_LENGTH),
                    new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getLanguage(), FreeDriveMetaDataLengthConst.LANGUAGE_LENGTH),
                    new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getDriveId(), FreeDriveMetaDataLengthConst.DRIVEID_LENGTH)
            ));
    }

    @Override
    public String getFreeDriveDataString(Knowledge knowledge) {
        org.freecash.utils.ProtocolUtil.EncodeItem author = new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getAuthor(), FreeDriveDataLengthConst.AUTHOR_LENGTH);
        org.freecash.utils.ProtocolUtil.EncodeItem type = new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getType(), FreeDriveDataLengthConst.TYPE_LENGTH);
        org.freecash.utils.ProtocolUtil.EncodeItem title = new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getTitle(), FreeDriveDataLengthConst.TITLE_LENGTH);
        org.freecash.utils.ProtocolUtil.EncodeItem content = new org.freecash.utils.ProtocolUtil.EncodeItem(knowledge.getContent(), FreeDriveDataLengthConst.CONTENT_LENGTH);
        return org.freecash.utils.ProtocolUtil.encodeValue(Arrays.asList(author,type,title,content));
    }

}
