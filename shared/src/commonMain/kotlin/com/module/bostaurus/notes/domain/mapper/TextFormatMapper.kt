package com.module.bostaurus.notes.domain.mapper

import com.module.bostaurus.notes.data.model.TextFormatDataModel
import com.module.bostaurus.notes.domain.model.TextFormatDomainModel

class TextFormatMapper {
    fun mapToDomainModel(dataModel: TextFormatDataModel): TextFormatDomainModel {
        return TextFormatDomainModel(
            range = dataModel.range,
            isBold = dataModel.isBold,
            isItalic = dataModel.isItalic,
            isUnderline = dataModel.isUnderline,
            textSize = dataModel.textSize
        )
    }

    fun mapToDataModel(domainModel: TextFormatDomainModel): TextFormatDataModel {
        return TextFormatDataModel(
            range = domainModel.range,
            isBold = domainModel.isBold,
            isItalic = domainModel.isItalic,
            isUnderline = domainModel.isUnderline,
            textSize = domainModel.textSize
        )
    }
}