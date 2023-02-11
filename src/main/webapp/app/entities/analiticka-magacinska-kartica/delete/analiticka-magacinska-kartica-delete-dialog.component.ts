import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';
import { AnalitickaMagacinskaKarticaService } from '../service/analiticka-magacinska-kartica.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './analiticka-magacinska-kartica-delete-dialog.component.html',
})
export class AnalitickaMagacinskaKarticaDeleteDialogComponent {
  analitickaMagacinskaKartica?: IAnalitickaMagacinskaKartica;

  constructor(protected analitickaMagacinskaKarticaService: AnalitickaMagacinskaKarticaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.analitickaMagacinskaKarticaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
