import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnalitickaMagacinskaKarticaComponent } from './list/analiticka-magacinska-kartica.component';
import { AnalitickaMagacinskaKarticaDetailComponent } from './detail/analiticka-magacinska-kartica-detail.component';
import { AnalitickaMagacinskaKarticaUpdateComponent } from './update/analiticka-magacinska-kartica-update.component';
import { AnalitickaMagacinskaKarticaDeleteDialogComponent } from './delete/analiticka-magacinska-kartica-delete-dialog.component';
import { AnalitickaMagacinskaKarticaRoutingModule } from './route/analiticka-magacinska-kartica-routing.module';

@NgModule({
  imports: [SharedModule, AnalitickaMagacinskaKarticaRoutingModule],
  declarations: [
    AnalitickaMagacinskaKarticaComponent,
    AnalitickaMagacinskaKarticaDetailComponent,
    AnalitickaMagacinskaKarticaUpdateComponent,
    AnalitickaMagacinskaKarticaDeleteDialogComponent,
  ],
})
export class AnalitickaMagacinskaKarticaModule {}
