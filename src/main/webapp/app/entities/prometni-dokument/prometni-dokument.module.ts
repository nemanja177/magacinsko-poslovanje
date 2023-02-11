import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrometniDokumentComponent } from './list/prometni-dokument.component';
import { PrometniDokumentDetailComponent } from './detail/prometni-dokument-detail.component';
import { PrometniDokumentUpdateComponent } from './update/prometni-dokument-update.component';
import { PrometniDokumentDeleteDialogComponent } from './delete/prometni-dokument-delete-dialog.component';
import { PrometniDokumentRoutingModule } from './route/prometni-dokument-routing.module';

@NgModule({
  imports: [SharedModule, PrometniDokumentRoutingModule],
  declarations: [
    PrometniDokumentComponent,
    PrometniDokumentDetailComponent,
    PrometniDokumentUpdateComponent,
    PrometniDokumentDeleteDialogComponent,
  ],
})
export class PrometniDokumentModule {}
