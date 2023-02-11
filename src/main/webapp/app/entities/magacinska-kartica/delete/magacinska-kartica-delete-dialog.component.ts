import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMagacinskaKartica } from '../magacinska-kartica.model';
import { MagacinskaKarticaService } from '../service/magacinska-kartica.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './magacinska-kartica-delete-dialog.component.html',
})
export class MagacinskaKarticaDeleteDialogComponent {
  magacinskaKartica?: IMagacinskaKartica;

  constructor(protected magacinskaKarticaService: MagacinskaKarticaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.magacinskaKarticaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
