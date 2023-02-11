import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrometniDokument } from '../prometni-dokument.model';
import { PrometniDokumentService } from '../service/prometni-dokument.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './prometni-dokument-delete-dialog.component.html',
})
export class PrometniDokumentDeleteDialogComponent {
  prometniDokument?: IPrometniDokument;

  constructor(protected prometniDokumentService: PrometniDokumentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prometniDokumentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
